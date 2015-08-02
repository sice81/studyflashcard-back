package com.genius.flashcard.api.auth.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.genius.flashcard.common.enums.UserAccountTypeCdEnum;
import com.genius.flashcard.common.enums.UserStatusEnum;

@Entity
@Table(name = "USERS")
public class User {

	private static final String BASE_PATH = "com.genius.flashcard.hibernate.type.";

	@Id
	@Column(name = "USER_ID", length = 50)
	String userId;

	@Column(name = "PASSWORD", length = 500)
	String password;

	@Column(name = "USER_NAME", length = 500)
	String userName;

	@Column(name = "USER_EMAIL", length = 500)
	String userEmail;

	/**
	 * 프로필 사진 URL
	 */
	@Column(name = "PROFILE_PIC_URL", length = 4000)
	String profilePictureUrl;

	/**
	 * 유저 계정 구분코드
	 */
	@Type(type = BASE_PATH + "UserAccountTypeCdEnumType")
	@Column(name = "USER_ACCOUNT_TYPE_CD", length = 20)
	UserAccountTypeCdEnum userAccountTypeCd;

	/**
	 * 유저 상태코드
	 */
	@Type(type = BASE_PATH + "UserStatusEnumType")
	@Column(name = "USER_STATUS_CD", length = 20)
	UserStatusEnum userStatus;

	/**
	 * 외부유저ID
	 */
	@Column(name = "EXTERN_USER_ID", length = 500)
	String externUserId;

	/**
	 * 최근 접속일시
	 */
	@Column(name = "LAST_CONNECTED_DATE")
	Date lastConnectedDate;

	/**
	 * 수정일시
	 */
	@Column(name = "MODIFIED_DATE")
	Date modifiedDate;

	/**
	 * 생성일시
	 */
	@Column(name = "CREATED_DATE")
	Date createdDate;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getProfilePictureUrl() {
		return profilePictureUrl;
	}

	public void setProfilePictureUrl(String profilePictureUrl) {
		this.profilePictureUrl = profilePictureUrl;
	}

	public UserAccountTypeCdEnum getUserAccountTypeCd() {
		return userAccountTypeCd;
	}

	public void setUserAccountTypeCd(UserAccountTypeCdEnum userAccountTypeCd) {
		this.userAccountTypeCd = userAccountTypeCd;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastConnectedDate() {
		return lastConnectedDate;
	}

	public void setLastConnectedDate(Date lastConnectedDate) {
		this.lastConnectedDate = lastConnectedDate;
	}
}
