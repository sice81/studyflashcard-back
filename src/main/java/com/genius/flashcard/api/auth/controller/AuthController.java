package com.genius.flashcard.api.auth.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.genius.flashcard.api.auth.UserDao;
import com.genius.flashcard.api.auth.dto.User;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	Logger logger = Logger.getLogger(this.getClass().getName());
	
	@Autowired
	UserDao userDao;

	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public User signin(@RequestBody User user) {
		logger.info("/api/auth/signin");
		userDao.saveUser(user);
		return user;
	}

	@RequestMapping(value = "/test")
	public User test() {
		System.out.println("test");
		User user = new User();
		user.setUserId("userId");
		return user;
	}
}
