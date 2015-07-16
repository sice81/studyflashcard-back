package com.genius.flashcard.api.auth.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.genius.flashcard.api.auth.UserDao;
import com.genius.flashcard.api.auth.dto.User;
import com.genius.flashcard.api.auth.service.FacebookValidateService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	Logger logger = Logger.getLogger(this.getClass().getName());
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	FacebookValidateService facebookValidateService;

	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public User signin(@RequestBody User user) throws Exception {
		userDao.saveUser(user);
		return user;
	}

	/**
	 * 페이스북 javascript api로 받은 값을 검증한다.
	 * 
	 * @param accessToken 페이스북 access token
	 * @param userId 페이스북 userId
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/facebook", method = RequestMethod.POST)
	public boolean facebook(@RequestParam String accessToken, @RequestParam String userId) throws Exception {
		return facebookValidateService.validate(accessToken, userId);
	}
}
