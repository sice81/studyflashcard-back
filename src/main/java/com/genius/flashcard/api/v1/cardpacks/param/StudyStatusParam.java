package com.genius.flashcard.api.v1.cardpacks.param;

import javax.persistence.Entity;

@Entity
public class StudyStatusParam {
	String[] rights;

	String[] wrongs;

	String current;

	StudyActLogParam studyActLog;

	public String[] getRights() {
		return rights;
	}

	public void setRights(String[] rights) {
		this.rights = rights;
	}

	public String[] getWrongs() {
		return wrongs;
	}

	public void setWrongs(String[] wrongs) {
		this.wrongs = wrongs;
	}

	public String getCurrent() {
		return current;
	}

	public void setCurrent(String current) {
		this.current = current;
	}

	public StudyActLogParam getStudyActLog() {
		return studyActLog;
	}

	public void setStudyActLog(StudyActLogParam studyActLog) {
		this.studyActLog = studyActLog;
	}

	public static class StudyActLogParam {
		int wrongCnt;

		int rightCnt;

		int cardViewCnt;

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

		public int getCardViewCnt() {
			return cardViewCnt;
		}

		public void setCardViewCnt(int cardViewCnt) {
			this.cardViewCnt = cardViewCnt;
		}
	}
}
