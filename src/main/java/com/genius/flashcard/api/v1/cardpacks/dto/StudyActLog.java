package com.genius.flashcard.api.v1.cardpacks.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 학습행태기록
 *
 * @author 박재익
 *
 */
@Entity
@Table(name = "STUDY_ACT_LOG")
public class StudyActLog implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 2837235471871053517L;

	/**
	 * 생성일시
	 */
	@Id
	@Column(name = "CREATED_DATE")
	Date createdDate;

	/**
	 * 유저ID
	 */
	@Id
	@Column(name = "USER_ID", length = 50)
	String userId;

	/**
	 * 카드팩ID
	 */
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
	 * 후면 보기횟수
	 */
	@Column(name = "BACK_VIEW_CNT", length = 5)
	int backViewCnt = 0;

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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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

	public int getBackViewCnt() {
		return backViewCnt;
	}

	public void setBackViewCnt(int backViewCnt) {
		this.backViewCnt = backViewCnt;
	}
}
