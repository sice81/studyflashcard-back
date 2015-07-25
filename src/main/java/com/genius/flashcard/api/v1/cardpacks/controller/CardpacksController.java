package com.genius.flashcard.api.v1.cardpacks.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/api/app/v1")
public class CardpacksController {
	Logger logger = Logger.getLogger(this.getClass().getName());

	@Autowired
	CardpackService cardpackService;

	@Autowired
	CardpackDao cardpackDao;

	@RequestMapping(value = "/users/{userId}/cardpacks", method = RequestMethod.POST)
	public Map<String, Object> create(@PathVariable String userId, @RequestBody CardpackParam cardpackParam, @CurrentUser User user, HttpServletResponse res) throws Exception {
		Assert.isTrue(cardpackService.isCanCreate(userId, user), "You don't have permission!");
		Assert.isTrue(userId.length() > 0, "UserId is empty!");
		Assert.isTrue(cardpackParam.getCardpackName().length() > 0, "Name is empty!");
		Assert.isTrue(cardpackParam.getDocData().length() > 0, "Name is empty!");

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
		Assert.isTrue(cardpackService.isCanCreate(userId, user), "You don't have permission!");
		Assert.isTrue(userId.length() > 0, "UserId is empty!");

		return cardpackService.findByUserId(user.getUserId());
	}

	@RequestMapping(value = "/users/{userId}/cardpacks/{cardpackId}", method = RequestMethod.GET)
	public String get(@PathVariable String userId, @PathVariable String cardpackId, @CurrentUser User user) throws Exception {
		Assert.isTrue(cardpackService.isCanCreate(userId, user), "You don't have permission!");
		Assert.isTrue(userId.length() > 0, "UserId is empty!");

		Cardpack card = cardpackDao.get(cardpackId);
		return card.getDocData();
	}

}
