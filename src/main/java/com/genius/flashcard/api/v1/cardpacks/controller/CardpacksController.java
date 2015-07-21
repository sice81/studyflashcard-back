package com.genius.flashcard.api.v1.cardpacks.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.genius.flashcard.annotation.CurrentUser;
import com.genius.flashcard.api.auth.dto.User;
import com.genius.flashcard.api.v1.cardpacks.dto.Cardpack;
import com.genius.flashcard.api.v1.cardpacks.param.CardpackParam;
import com.genius.flashcard.api.v1.cardpacks.service.CardpackService;

@RestController
@RequestMapping("/api/app/v1")
public class CardpacksController {
	Logger logger = Logger.getLogger(this.getClass().getName());

	@Autowired
	CardpackService cardpackService;

	@RequestMapping(value = "/users/{userId}/cardpacks", method = RequestMethod.POST)
	public Cardpack create(@PathVariable String userId, @RequestBody CardpackParam cardpackParam, @CurrentUser User user) throws Exception {
		Assert.isTrue(cardpackService.isCanCreate(userId, user), "You don't have permission!");
		Assert.isTrue(userId.length() > 0, "UserId is empty!");
		Assert.isTrue(cardpackParam.getCardpackName().length() > 0, "Name is empty!");
		Assert.isTrue(cardpackParam.getDocData().length() > 0, "Name is empty!");

		return cardpackService.create(cardpackParam, userId, user);
	}

}
