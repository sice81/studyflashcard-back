package com.genius.flashcard.common.entity;

import java.util.Date;

import com.genius.flashcard.api.auth.dto.User;

public class UserSession {
	/**
	 * 유저 정보
	 */
	User user;
	
	/**
	 * 세션ID
	 */
	String sessionId;
	
	/**
	 * 세션 생성일시
	 */
	Date createdDate;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
}
