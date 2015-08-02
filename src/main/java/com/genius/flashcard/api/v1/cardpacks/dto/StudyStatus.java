package com.genius.flashcard.api.v1.cardpacks.dto;

import java.io.Serializable;
import java.util.Date;

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
	@Column(name = "USER_ID", length = 50)
	String userId;

	@Id
	@Column(name = "CARDPACK_ID", length = 20)
	String cardpackId;

	/**
	 * 틀림수
	 */
	@Column(name = "WRONG_CNT", length = 5)
	int wrongCnt = 0;

	/**
	 * 맞음수
	 */
	@Column(name = "RIGHT_CNT", length = 5)
	int rightCnt = 0;

	/**
	 * 학습상태코드
	 */
	@Type(type = BASE_PATH + "StudyStatusCdEnumType")
	@Column(name = "STUDY_STATUS_CD", length = 20)
	StudyStatusCdEnum studyStatusCd;

	/**
	 * S3 키
	 */
	@Column(name = "S3_KEY", length = 500)
	String s3Key;

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


	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setRightCnt(int rightCnt) {
		this.rightCnt = rightCnt;
	}

	public StudyStatusCdEnum getStudyStatusCd() {
		return studyStatusCd;
	}

	public void setStudyStatusCd(StudyStatusCdEnum studyStatusCd) {
		this.studyStatusCd = studyStatusCd;
	}

	public String getS3Key() {
		return s3Key;
	}

	public void setS3Key(String s3Key) {
		this.s3Key = s3Key;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

}
