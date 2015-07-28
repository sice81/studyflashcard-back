package com.genius.flashcard.api.v1.cardpacks.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.genius.flashcard.common.enums.StudyStatusCdEnum;

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

	private static final String BASE_PATH = "com.genius.flashcard.hibernate.type.";

	@Id
	@Column(name = "USER_ID")
	String userId;

	@Id
	@Column(name = "CARDPACK_ID")
	String cardpackId;

	/**
	 * 틀림수
	 */
	@Column(name = "WRONG_CNT")
	int wrongCnt;

	/**
	 * 맞음수
	 */
	@Column(name = "RIGHT_CNT")
	int rightCnt;

	/**
	 * 학습상태코드
	 */
	@Type(type = BASE_PATH + "StudyStatusCdEnumType")
	@Column(name = "STATUS_CD")
	StudyStatusCdEnum statusCd;

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

	public int getWrongCnt() {
		return wrongCnt;
	}

	public void setWrongCnt(int wrongCnt) {
		this.wrongCnt = wrongCnt;
	}

	public int getRightCnt() {
		return rightCnt;
	}

	public void setRightCnt(int rightCnt) {
		this.rightCnt = rightCnt;
	}

	public StudyStatusCdEnum getStatusCd() {
		return statusCd;
	}

	public void setStatusCd(StudyStatusCdEnum statusCd) {
		this.statusCd = statusCd;
	}

	public String getS3Key() {
		return s3Key;
	}

	public void setS3Key(String s3Key) {
		this.s3Key = s3Key;
	}

}
