package com.genius.flashcard.api.v1.cardpacks.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 학습상태
 *
 * @author 박재익
 *
 */
@Entity
@Table(name = "STUDY_STATUS")
public class StudyStatus implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 3727135471879053557L;

	@Id
	@Column(name = "USER_ID")
	String userId;

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
