package com.genius.flashcard.auth;

import org.springframework.security.core.token.Token;

import com.genius.flashcard.api.auth.dto.User;

public class MyToken {
	Token token;
	User user;
	
	public Token getToken() {
		return token;
	}
	public void setToken(Token token) {
		this.token = token;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
