package com.genius.flashcard.api.v1.cardpacks.dto;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 학습행동 집계
 *
 * @author 박재익
 *
 */
@Entity
@Table(name = "STUDY_ACT_LOG")
public class StudyActLogStatistics {
	String date;
	long wrongCnt;
	long rightCnt;
	long backViewCnt;

	public StudyActLogStatistics() {
		super();
	}

	public StudyActLogStatistics(long wrongCnt, long rightCnt, long backViewCnt) {
		super();
		this.wrongCnt = wrongCnt;
		this.rightCnt = rightCnt;
		this.backViewCnt = backViewCnt;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public long getWrongCnt() {
		return wrongCnt;
	}

	public void setWrongCnt(long wrongCnt) {
		this.wrongCnt = wrongCnt;
	}

	public long getRightCnt() {
		return rightCnt;
	}

	public void setRightCnt(long rightCnt) {
		this.rightCnt = rightCnt;
	}

	public long getBackViewCnt() {
		return backViewCnt;
	}

	public void setBackViewCnt(long backViewCnt) {
		this.backViewCnt = backViewCnt;
	}
}
