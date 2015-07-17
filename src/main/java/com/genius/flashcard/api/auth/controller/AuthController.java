package com.genius.flashcard.api.auth.controller;

import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.genius.flashcard.api.auth.dao.UserDao;
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
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public List<User> users() throws Exception {
		return userDao.findAll();
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
	public boolean facebook(@RequestParam String accessToken, @RequestParam String userId, HttpServletRequest request) throws Exception {
		boolean result = facebookValidateService.validate(accessToken, userId);
		
//		if (result) {
//			UserSession us = new UserSession();
//			us.setSessionId(request.getCookies());
//			
////			request.setSession();
//		}
		
		return result;
	}
}
