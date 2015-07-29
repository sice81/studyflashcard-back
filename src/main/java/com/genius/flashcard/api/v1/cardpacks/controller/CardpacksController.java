package com.genius.flashcard.api.v1.cardpacks.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
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
import com.genius.flashcard.api.auth.dto.User;
import com.genius.flashcard.api.v1.cardpacks.dao.CardpackDao;
import com.genius.flashcard.api.v1.cardpacks.dao.StudyActLogDao;
import com.genius.flashcard.api.v1.cardpacks.dao.StudyStatusDao;
import com.genius.flashcard.api.v1.cardpacks.dto.Cardpack;
import com.genius.flashcard.api.v1.cardpacks.dto.StudyActLog;
import com.genius.flashcard.api.v1.cardpacks.dto.StudyStatus;
import com.genius.flashcard.api.v1.cardpacks.param.CardpackParam;
import com.genius.flashcard.api.v1.cardpacks.param.StudyStatusParam;
import com.genius.flashcard.api.v1.cardpacks.param.StudyStatusParam.StudyActLogParam;
import com.genius.flashcard.api.v1.cardpacks.service.CardpackService;
import com.genius.flashcard.api.v1.cardpacks.service.S3SendService;
import com.genius.flashcard.api.v1.cardpacks.service.StudyStatusService;
import com.genius.flashcard.common.enums.StudyStatusCdEnum;

@RestController
@RequestMapping("/api/app/v1")
public class CardpacksController {
	Logger logger = Logger.getLogger(this.getClass().getName());

	@Autowired
	CardpackService cardpackService;

	@Autowired
	CardpackDao cardpackDao;

	@Autowired
	S3SendService s3SendService;

	@Autowired
	StudyStatusDao studyStatusDao;

	@Autowired
	StudyStatusService studyStatusService;

	@Autowired
	StudyActLogDao studyActLogDao;

	@Autowired
	MappingJackson2HttpMessageConverter converter;

	@Value("${app.step}")
	String APP_STEP;

	@RequestMapping(value = "/users/{userId}/cardpacks", method = RequestMethod.POST)
	public Map<String, Object> create(@PathVariable String userId, @RequestBody CardpackParam cardpackParam,
			@CurrentUser User user, HttpServletResponse res) throws Exception {
		Assert.isTrue(cardpackService.isCanCreate(userId, user), "You don't have permission!");
		Assert.isTrue(userId.length() > 0, "UserId is empty!");
		Assert.isTrue(cardpackParam.getCardpackName().length() > 0, "Name is empty!");
		Assert.isTrue(cardpackParam.getDocData().length() > 0, "Name is empty!");

		String yyyyMm = new SimpleDateFormat("yyyyMM").format(new Date());
		String dd = new SimpleDateFormat("dd").format(new Date());
		String dir = "Cardpacks/" + yyyyMm + "/" + dd;
		String keyName = String.format("%s/%s", dir, UUID.randomUUID().toString());
		s3SendService.send(cardpackParam.getDocData(), keyName);

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
	public Map<String, Object> put(@PathVariable String userId, @PathVariable String cardpackId,
			@RequestBody CardpackParam cardpackParam, @CurrentUser User user, HttpServletResponse res)
					throws Exception {
		Assert.isTrue(cardpackService.isCanCreate(userId, user), "You don't have permission!");
		Assert.isTrue(userId.length() > 0, "UserId is empty!");
		Assert.isTrue(cardpackParam.getCardpackName().length() > 0, "Name is empty!");
		Assert.isTrue(cardpackParam.getDocData().length() > 0, "Name is empty!");

		Cardpack c = cardpackDao.get(cardpackId);
		s3SendService.send(cardpackParam.getDocData(), c.getS3Key());

		cardpackParam.setS3Key(c.getS3Key());
		cardpackService.save(cardpackParam, cardpackId, userId, user);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cardpackId", c.getCardpackId());
		return map;
	}

	@RequestMapping(value = "/users/{userId}/cardpacks", method = RequestMethod.GET)
	public List<Cardpack> getUserCardpacks(@PathVariable String userId, @CurrentUser User user) throws Exception {
		Assert.isTrue(cardpackService.isCanGet(userId, user), "You don't have permission!");
		Assert.isTrue(userId.length() > 0, "UserId is empty!");

		return cardpackService.findByUserId(user.getUserId());
	}

	@RequestMapping(value = "/users/{userId}/studyAct/statistics", method = RequestMethod.GET)
	public List<Map<String, Object>> getStudyActStatistics(@PathVariable String userId,
			@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate,
			@RequestParam(required = false) String date,
			@RequestParam(defaultValue="day") String type,
			@CurrentUser User user) throws Exception {
		Assert.isTrue(cardpackService.isCanGet(userId, user), "You don't have permission!");
		Assert.isTrue(userId.length() > 0, "UserId is empty!");

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		// TODO 목업데이터 삭제할 것...
		for (int i=0; i<7; i++) {
			map = new HashMap<String, Object>();
			map.put("date", "2015072" + i);
			map.put("wrongCnt", Math.abs(new Random().nextInt() % 10));
			map.put("rightCnt", Math.abs(new Random().nextInt() % 10));
			map.put("backViewCnt", 10 + Math.abs(new Random().nextInt() % 10));
			list.add(map);
		}

		return list;
	}


	@RequestMapping(value = "/users/{userId}/cardpacks/{cardpackId}/doc", method = RequestMethod.GET)
	public Map<String, Object> get(@PathVariable String userId, @PathVariable String cardpackId, @CurrentUser User user)
			throws Exception {
		Assert.isTrue(cardpackService.isCanGet(cardpackId, user), "You don't have permission!");
		Assert.isTrue(userId.length() > 0, "UserId is empty!");

		Cardpack card = cardpackDao.get(cardpackId);
		String signedUrl = s3SendService.getSignedUrl(card.getS3Key());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("s3Url", signedUrl);
		return map;
	}

	@RequestMapping(value = "/users/{userId}/cardpacks/{cardpackId}/status", method = RequestMethod.GET)
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

	@RequestMapping(value = "/users/{userId}/cardpacks/{cardpackId}/status", method = RequestMethod.PUT)
	public void putStudyStatus(@PathVariable String userId, @PathVariable String cardpackId,
			@RequestBody StudyStatusParam studyStatusParam, @CurrentUser User user) throws Exception {
		Assert.isTrue(cardpackService.isCanGet(cardpackId, user), "You don't have permission!");
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

		s3SendService.send(json, keyName);

		studyStatusService.save(studyStatusParam, cardpackId, keyName, userId, user);

		StudyActLogParam act = studyStatusParam.getStudyActLog();
		if (act != null) {
			StudyActLog studyActLog = new StudyActLog();
			studyActLog.setUserId(userId);
			studyActLog.setCardpackId(cardpackId);
			studyActLog.setCreatedDate(new Date());
			studyActLog.setWrongCnt(act.getWrongCnt());
			studyActLog.setRightCnt(act.getRightCnt());
			studyActLog.setBackViewCnt(act.getCardViewCnt());

			if (act.getRightCnt() > 0 || act.getWrongCnt() > 0 || act.getCardViewCnt() > 0) {
				studyActLogDao.save(studyActLog);
			}
		}
	}

	@RequestMapping(value = "/cardpacks/{cardpackId}", method = RequestMethod.GET)
	public Map<String, Object> getCardpack(@PathVariable String cardpackId, @CurrentUser User user) throws Exception {
		Assert.isTrue(cardpackService.isCanGet(cardpackId, user), "You don't have permission!");

		Cardpack card = cardpackDao.get(cardpackId);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cardpackName", card.getCardpackName());
		map.put("ownerUserId", card.getOwnerUserId());
		map.put("cardCnt", card.getCardCnt());
		map.put("inStudyUserCnt", studyStatusDao.getCountInStudyUserCnt(cardpackId, StudyStatusCdEnum.IN_STUDY));
		map.put("completeStudyUserCnt", studyStatusDao.getCountInStudyUserCnt(cardpackId, StudyStatusCdEnum.COMPLETE));
		map.put("accessCd", card.getAccessCd());

		return map;
	}

	@RequestMapping(value = "/cardpacks/{cardpackId}/doc", method = RequestMethod.GET)
	public Map<String, Object> getCardpackDoc(@PathVariable String cardpackId, @CurrentUser User user)
			throws Exception {
		Assert.isTrue(cardpackService.isCanGet(cardpackId, user), "You don't have permission!");

		Cardpack card = cardpackDao.get(cardpackId);
		String signedUrl = s3SendService.getSignedUrl(card.getS3Key());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("docVer", card.getDocVer());
		map.put("s3Url", signedUrl);
		return map;
	}
}
