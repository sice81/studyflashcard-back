package com.genius.flashcard.api.auth.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.genius.flashcard.common.enums.UserAccountTypeEnum;
import com.genius.flashcard.common.enums.UserStatusEnum;

@Entity
@Table(name = "USERS")
public class User {

	private static final String BASE_PATH = "com.genius.flashcard.hibernate.type.";

	@Id
	@Column(name = "USER_ID")
	String userId;

	@Column(name = "USER_NAME")
	String userName;

	@Type(type = BASE_PATH + "UserAccountTypeEnumType")
	@Column(name = "USER_ACCOUNT_TYPE_CD")
	UserAccountTypeEnum userAccountType;

	@Type(type = BASE_PATH + "UserStatusEnumType")
	@Column(name = "USER_STATUS_CD")
	UserStatusEnum userStatus;

	@Column(name = "EXTERN_USER_ID")
	String externUserId;

	@Column(name = "USER_EMAIL")
	String userEmail;

	@Column(name = "PASSWORD")
	String password;

	@Column(name = "CREATED_DATE")
	Date createdDate;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public UserAccountTypeEnum getUserAccountType() {
		return userAccountType;
	}

	public void setUserAccountType(UserAccountTypeEnum userAccountType) {
		this.userAccountType = userAccountType;
	}

	public UserStatusEnum getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(UserStatusEnum userStatus) {
		this.userStatus = userStatus;
	}

	public String getExternUserId() {
		return externUserId;
	}

	public void setExternUserId(String externUserId) {
		this.externUserId = externUserId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

}
