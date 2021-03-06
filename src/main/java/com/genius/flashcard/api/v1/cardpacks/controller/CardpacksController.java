package com.genius.flashcard.api.v1.cardpacks.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.genius.flashcard.annotation.CurrentUser;
import com.genius.flashcard.api.auth.dao.UserDao;
import com.genius.flashcard.api.auth.dto.User;
import com.genius.flashcard.api.v1.cardpacks.dao.CardpackDao;
import com.genius.flashcard.api.v1.cardpacks.dao.StudyActLogDao;
import com.genius.flashcard.api.v1.cardpacks.dao.StudyActLogStatisticsDao;
import com.genius.flashcard.api.v1.cardpacks.dao.StudyStatusDao;
import com.genius.flashcard.api.v1.cardpacks.dto.Cardpack;
import com.genius.flashcard.api.v1.cardpacks.dto.StudyActLog;
import com.genius.flashcard.api.v1.cardpacks.dto.StudyActLogStatistics;
import com.genius.flashcard.api.v1.cardpacks.dto.StudyStatus;
import com.genius.flashcard.api.v1.cardpacks.param.CardpackParam;
import com.genius.flashcard.api.v1.cardpacks.param.StudyStatusParam;
import com.genius.flashcard.api.v1.cardpacks.param.StudyStatusParam.StudyActLogParam;
import com.genius.flashcard.api.v1.cardpacks.service.CardpackService;
import com.genius.flashcard.api.v1.cardpacks.service.S3Service;
import com.genius.flashcard.api.v1.cardpacks.service.StudyActLogStatisticsService;
import com.genius.flashcard.api.v1.cardpacks.service.StudyStatusService;
import com.genius.flashcard.common.enums.CardpackAccessCdEnum;
import com.genius.flashcard.common.enums.StudyStatusCdEnum;
import com.genius.flashcard.utils.DateHelper;

@RestController
@RequestMapping("/api/app/v1")
public class CardpacksController {
	Logger logger = Logger.getLogger(this.getClass().getName());

	@Autowired
	UserDao userDao;

	@Autowired
	CardpackService cardpackService;

	@Autowired
	CardpackDao cardpackDao;

	@Autowired
	S3Service s3SendService;

	@Autowired
	StudyStatusDao studyStatusDao;

	@Autowired
	StudyStatusService studyStatusService;

	@Autowired
	StudyActLogDao studyActLogDao;

	@Autowired
	StudyActLogStatisticsDao studyActLogStatisticsDao;

	@Autowired
	StudyActLogStatisticsService studyActLogStatisticsService;


	@Autowired
	MappingJackson2HttpMessageConverter converter;

	@Value("${app.step}")
	String APP_STEP;

	@RequestMapping(value = "/users/{userId}/cardpacks", method = RequestMethod.POST)
	public Map<String, Object> postCardpack(@PathVariable String userId, @RequestBody CardpackParam cardpackParam,
			@CurrentUser User user, HttpServletResponse res) throws Exception {
		Assert.isTrue(userId.length() > 0, "UserId is empty!");
		Assert.isTrue(cardpackParam.getCardpackName().length() > 0, "Name is empty!");
		Assert.isTrue(cardpackParam.getDocData().length() > 0, "Name is empty!");
		Assert.isTrue(cardpackService.isCanCreate(userId, user), "You don't have permission!");

		String yyyyMm = new SimpleDateFormat("yyyyMM").format(new Date());
		String dd = new SimpleDateFormat("dd").format(new Date());
		String dir = "Cardpacks/" + yyyyMm + "/" + dd;
		String keyName = String.format("%s/%s", dir, UUID.randomUUID().toString());

		Assert.isTrue(s3SendService.sendDoc(cardpackParam.getDocData(), keyName), "Upload failed!");

		cardpackParam.setS3Key(keyName);
		String cardpackId = cardpackService.create(cardpackParam, userId, user);

		if (cardpackId != null) {
			res.setStatus(HttpStatus.CREATED.value());
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cardpackId", cardpackId);
		return map;
	}

	@RequestMapping(value = "/users/{userId}/cardpacks/{cardpackId}", method = RequestMethod.PUT)
	public Map<String, Object> putCardpack(@PathVariable String userId, @PathVariable String cardpackId,
			@RequestBody CardpackParam cardpackParam, @CurrentUser User user, HttpServletResponse res)
					throws Exception {
		Assert.isTrue(userId.length() > 0, "UserId is empty!");
		Assert.isTrue(cardpackParam.getCardpackName().length() > 0, "Name is empty!");
		Assert.isTrue(cardpackParam.getDocData().length() > 0, "Name is empty!");
		Assert.isTrue(cardpackService.isCanModify(cardpackId, userId, user), "You don't have permission!");

		Cardpack c = cardpackDao.get(cardpackId);
		Assert.isTrue(s3SendService.sendDoc(cardpackParam.getDocData(), c.getS3Key()), "Upload failed!");

		cardpackParam.setS3Key(c.getS3Key());
		cardpackService.save(cardpackParam, cardpackId, userId, user);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cardpackId", c.getCardpackId());
		return map;
	}

	@RequestMapping(value = "/users/{userId}/cardpacks", method = RequestMethod.GET)
	public List<Cardpack> getUserCardpackList(@PathVariable String userId, @CurrentUser User user) throws Exception {
		Assert.isTrue(userId.length() > 0, "UserId is empty!");
		Assert.isTrue(cardpackService.isCanGet(userId, user), "You don't have permission!");

		return cardpackService.findByUserId(user.getUserId());
	}

	@RequestMapping(value = "/users/{userId}/studyAct/statistics", method = RequestMethod.GET)
	public List<Map<String, Object>> getStudyActStatistics(@PathVariable String userId,
			@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate,
			@RequestParam(required = false) String date,
			@RequestParam(defaultValue="day") String type,
			@CurrentUser User user) throws Exception {
		Assert.isTrue(userId.length() > 0, "UserId is empty!");
		Assert.isTrue(cardpackService.isCanGet(userId, user), "You don't have permission!");

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		DateHelper t = new DateHelper().addDate(1);
		DateHelper d = new DateHelper(String.format("%04d%02d%02d", t.getYear(), t.getMonth(), t.getDate()));

		Date eDate = d.getTime();
		Date sDate = d.addDate(-7).getTime();

		// -7일부터 현재시간까지
		List<StudyActLogStatistics> listStat = studyActLogStatisticsService.findDays(userId, sDate, eDate);

		for (StudyActLogStatistics e : listStat) {
			map = new HashMap<String, Object>();
			map.put("date", e.getDate());
			map.put("wrongCnt", e.getWrongCnt());
			map.put("rightCnt", e.getRightCnt());
			map.put("backViewCnt", e.getBackViewCnt());
			list.add(map);
		}

		return list;
	}


	@RequestMapping(value = "/users/{userId}/cardpacks/{cardpackId}/doc", method = RequestMethod.GET)
	public Map<String, Object> getUserCardpackDoc(@PathVariable String userId, @PathVariable String cardpackId, @CurrentUser User user)
			throws Exception {
		Assert.isTrue(userId.length() > 0, "UserId is empty!");
		Assert.isTrue(cardpackService.isCanGetCardpack(cardpackId, user), "You don't have permission!");

		Cardpack card = cardpackDao.get(cardpackId);
		String signedUrl = s3SendService.getSignedUrl(card.getS3Key());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("s3Url", signedUrl);
		return map;
	}

	@RequestMapping(value = "/users/{userId}/studystatus", method = RequestMethod.GET)
	public List<Map<String, Object>> getStudyStatusList(@PathVariable String userId,
			@RequestParam(required=false) String studyStatus,
			@CurrentUser User user) throws Exception {
		Assert.isTrue(cardpackService.isCanGet(userId, user), "You don't have permission!");
		Assert.isTrue(userId.length() > 0, "UserId is empty!");

		StudyStatusCdEnum studyStatusCd = StudyStatusCdEnum.IN_STUDY;

		if (studyStatus != null) {
			studyStatusCd = StudyStatusCdEnum.valueOf(studyStatus);
		}

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		List<StudyStatus> list = studyStatusDao.findByUserId(userId, studyStatusCd);

		for (StudyStatus e : list) {
			Map<String, Object> map = new HashMap<String, Object>();

			Cardpack cardpack = cardpackDao.get(e.getCardpackId());
			map.put("cardpackName", cardpack.getCardpackName());
			map.put("cardCnt", cardpack.getCardCnt());
			map.put("cardpackId", e.getCardpackId());
			map.put("rightCnt", e.getRightCnt());
			map.put("wrongCnt", e.getWrongCnt());
			map.put("studyStatusCd", e.getStudyStatusCd());
			result.add(map);
		}

		return result;
	}

	@RequestMapping(value = "/users/{userId}/cardpacks/{cardpackId}/studystatus", method = RequestMethod.GET)
	public String getStudyStatus(@PathVariable String userId, @PathVariable String cardpackId, @CurrentUser User user)
			throws Exception {
		Assert.isTrue(cardpackService.isCanGet(userId, user), "You don't have permission!");
		Assert.isTrue(userId.length() > 0, "UserId is empty!");
		Assert.isTrue(cardpackId.length() > 0, "CardpackId is empty!");

		String result = null;
		StudyStatus status = studyStatusDao.get(userId, cardpackId);

		if (status != null) {
			result = s3SendService.get(status.getS3Key());
		}

		return result;
	}

	@RequestMapping(value = "/users/{userId}/cardpacks/{cardpackId}/studystatus", method = RequestMethod.PUT)
	public void putUserCardpackStudyStatus(@PathVariable String userId, @PathVariable String cardpackId,
			@RequestBody StudyStatusParam studyStatusParam, @CurrentUser User user) throws Exception {
		Assert.isTrue(userId.length() > 0, "UserId is empty!");
		Assert.isTrue(cardpackId.length() > 0, "CardpackId is empty!");

		StudyStatus status = studyStatusDao.get(userId, cardpackId);
		String json = converter.getObjectMapper().writeValueAsString(studyStatusParam);
		String yyyyMm = new SimpleDateFormat("yyyyMM").format(new Date());
		String dd = new SimpleDateFormat("dd").format(new Date());
		String dir = "StudyStatus/" + yyyyMm + "/" + dd;
		String keyName = String.format("%s/%s", dir, UUID.randomUUID().toString());

		if (status != null) {
			if (status.getS3Key() != null) {
				keyName = status.getS3Key();
			}
		}

		Assert.isTrue(s3SendService.sendDoc(json, keyName), "Upload failed!");

		studyStatusService.save(studyStatusParam, cardpackId, keyName, userId, user);

		StudyActLogParam act = studyStatusParam.getStudyActLog();
		if (act != null) {
			if (act.getRightCnt() > 0 || act.getWrongCnt() > 0 || act.getCardViewCnt() > 0) {
				StudyActLog studyActLog = new StudyActLog();
				studyActLog.setUserId(userId);
				studyActLog.setCardpackId(cardpackId);
				studyActLog.setCreatedDate(new Date());
				studyActLog.setWrongCnt(act.getWrongCnt());
				studyActLog.setRightCnt(act.getRightCnt());
				studyActLog.setBackViewCnt(act.getCardViewCnt());

				studyActLogDao.insert(studyActLog);
			}
		}
	}

	@RequestMapping(value = "/cardpacks/{cardpackId}", method = RequestMethod.GET)
	public Map<String, Object> getCardpack(@PathVariable String cardpackId, @CurrentUser User user) throws Exception {
		Assert.isTrue(cardpackId.length() > 0, "CardpackId is empty!");
//		Assert.isTrue(cardpackService.isCanGetCardpack(cardpackId, user), "You don't have permission!");

		Cardpack cardpack = cardpackDao.get(cardpackId);

		boolean isOwner = false;
		boolean isCanShow = false;

		if (user != null) {
			isOwner = cardpack.getOwnerUserId().equals(user.getUserId());
		}

		if (isOwner) {
			isCanShow = true;
		} else {
			if (cardpack.getCardpackAccessCd() == CardpackAccessCdEnum.PUBLIC) {
				isCanShow = true;
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();

		if (isCanShow) {
			User ownerUser = userDao.get(cardpack.getOwnerUserId());

			map.put("cardpackName", cardpack.getCardpackName());
			map.put("ownerUserName", ownerUser.getUserName());
			map.put("ownerUserId", cardpack.getOwnerUserId());
			map.put("ownerUserPicture", ownerUser.getProfilePictureUrl());
			map.put("cardCnt", cardpack.getCardCnt());
			map.put("inStudyUserCnt", studyStatusDao.getCountInStudyUserCnt(cardpackId, StudyStatusCdEnum.IN_STUDY));
			map.put("completeStudyUserCnt", studyStatusDao.getCountInStudyUserCnt(cardpackId, StudyStatusCdEnum.COMPLETE));
			map.put("cardpackAccessCd", cardpack.getCardpackAccessCd());
			map.put("isExposureStore", cardpack.isExposureStore());
			map.put("isAllowCopy", cardpack.isAllowCopy());

			// 갱신
			cardpack.setLastAccessDate(new Date());
			cardpackDao.updateWithoutEvict(cardpack);
		} else {
			map.put("cardpackAccessCd", cardpack.getCardpackAccessCd());
		}

		return map;
	}

	@RequestMapping(value = "/cardpacks/{cardpackId}/doc", method = RequestMethod.GET)
	public Map<String, Object> getCardpackDoc(@PathVariable String cardpackId, @CurrentUser User user)
			throws Exception {
		Assert.isTrue(cardpackId.length() > 0, "CardpackId is empty!");
//		Assert.isTrue(cardpackService.isCanGetCardpack(cardpackId, user), "You don't have permission!");

		Cardpack cardpack = cardpackDao.get(cardpackId);
		boolean isOwner = false;
		boolean isCanShow = false;

		if (user != null) {
			isOwner = cardpack.getOwnerUserId().equals(user.getUserId());
		}

		if (isOwner) {
			isCanShow = true;
		} else {
			if (cardpack.getCardpackAccessCd() == CardpackAccessCdEnum.PUBLIC) {
				isCanShow = true;
			}
		}

		Assert.isTrue(isCanShow, "You can't see this doc.");

		Map<String, Object> map = new HashMap<String, Object>();

		if (isCanShow) {
			String signedUrl = s3SendService.getSignedUrl(cardpack.getS3Key());
			map.put("docVer", cardpack.getDocVer());
			map.put("s3Url", signedUrl);

			// 갱신
			cardpack.setLastAccessDate(new Date());
			cardpackDao.updateWithoutEvict(cardpack);
		}

		return map;
	}
}
