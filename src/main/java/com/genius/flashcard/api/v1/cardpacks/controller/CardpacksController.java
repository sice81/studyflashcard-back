package com.genius.flashcard.api.v1.cardpacks.controller;

import java.text.SimpleDateFormat;
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
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.genius.flashcard.annotation.CurrentUser;
import com.genius.flashcard.api.auth.dto.User;
import com.genius.flashcard.api.v1.cardpacks.dao.CardpackDao;
import com.genius.flashcard.api.v1.cardpacks.dto.Cardpack;
import com.genius.flashcard.api.v1.cardpacks.param.CardpackParam;
import com.genius.flashcard.api.v1.cardpacks.service.CardpackService;
import com.genius.flashcard.api.v1.cardpacks.service.S3SendService;

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

	@Value("${app.step}")
	String APP_STEP;

	@RequestMapping(value = "/users/{userId}/cardpacks", method = RequestMethod.POST)
	public Map<String, Object> create(@PathVariable String userId, @RequestBody CardpackParam cardpackParam, @CurrentUser User user, HttpServletResponse res) throws Exception {
		Assert.isTrue(cardpackService.isCanCreate(userId, user), "You don't have permission!");
		Assert.isTrue(userId.length() > 0, "UserId is empty!");
		Assert.isTrue(cardpackParam.getCardpackName().length() > 0, "Name is empty!");
		Assert.isTrue(cardpackParam.getDocData().length() > 0, "Name is empty!");

		String dir = APP_STEP + "/" + new SimpleDateFormat("yyyyMM").format(new Date());
		String keyName = String.format("%s/%s", dir, UUID.randomUUID().toString());
		s3SendService.send(cardpackParam.getDocData(), keyName);

		cardpackParam.setS3Key(keyName);
		Cardpack c = cardpackService.create(cardpackParam, userId, user);

		if (c != null) {
			res.setStatus(HttpStatus.CREATED.value());
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cardpackId", c.getCardpackId());
		return map;
	}

	@RequestMapping(value = "/users/{userId}/cardpacks", method = RequestMethod.GET)
	public List<Cardpack> list(@PathVariable String userId, @CurrentUser User user) throws Exception {
		Assert.isTrue(cardpackService.isCanGet(userId, user), "You don't have permission!");
		Assert.isTrue(userId.length() > 0, "UserId is empty!");

		return cardpackService.findByUserId(user.getUserId());
	}

	@RequestMapping(value = "/users/{userId}/cardpacks/{cardpackId}", method = RequestMethod.GET)
	public Map<String, Object> get(@PathVariable String userId, @PathVariable String cardpackId, @CurrentUser User user, HttpServletResponse resp) throws Exception {
		Assert.isTrue(cardpackService.isCanGet(userId, user), "You don't have permission!");
		Assert.isTrue(userId.length() > 0, "UserId is empty!");

		Cardpack card = cardpackDao.get(cardpackId);
		String signedUrl = s3SendService.getSignedUrl(card.getS3Key());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("s3Url", signedUrl);
		return map;
	}

}
