package com.genius.flashcard.api.v1.cardpacks.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 학습상태
 * @author 박재익
 *
 */
@Entity
@Table(name = "STUDY_STATUS", indexes = @Index(columnList = "USER_ID"))
public class StudyStatus {

	private static final String BASE_PATH = "com.genius.flashcard.hibernate.type.";

	@Column(name = "USER_ID")
	String userId;

	/**
	 * 카드팩ID
	 */
	@Id
	@Column(name = "CARDPACK_ID")
	String cardpackId;

	/**
	 * S3 키
	 */
	@Column(name = "S3_KEY")
	String s3Key;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCardpackId() {
		return cardpackId;
	}

	public void setCardpackId(String cardpackId) {
		this.cardpackId = cardpackId;
	}

	public String getS3Key() {
		return s3Key;
	}

	public void setS3Key(String s3Key) {
		this.s3Key = s3Key;
	}

}
