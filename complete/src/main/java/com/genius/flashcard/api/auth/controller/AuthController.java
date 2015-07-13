package com.genius.flashcard.api.auth.controller;

import java.util.logging.Logger;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.genius.flashcard.api.auth.dto.User;

@RestController
// @RequestMapping("/api/auth")
public class AuthController {
	Logger logger = Logger.getLogger(this.getClass().getName());

	@RequestMapping(value = "/api/auth/signin", method = RequestMethod.POST)
	// public User signin(@RequestParam("userId") String userId,
	// @RequestParam("password") String password) {
	public User signin(@RequestBody User user) {
		System.out.println("/api/auth/signin");
//		User user = new User();
//		user.setUserId("u");
//		user.setPassword("p");
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
