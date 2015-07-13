package com.genius.flashcard.api.auth.controller;

import java.util.logging.Logger;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.genius.flashcard.api.auth.dto.User;

@RestController()
@RequestMapping("/api/auth")
public class AuthController {
	Logger logger = Logger.getLogger(this.getClass().getName());

	@RequestMapping(value="/signin", method=RequestMethod.POST)
	public User signin(@RequestParam("userId") String userId, @RequestParam("password") String password) {
		System.out.println("Hi2222!");
		User user = new User();
		user.setUserId(userId);
		user.setPassword(password);
		return user; 
	}
}
