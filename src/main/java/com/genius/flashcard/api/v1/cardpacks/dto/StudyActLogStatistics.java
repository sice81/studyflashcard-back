package com.genius.flashcard.api.v1.cardpacks.dto;

/**
 * 학습행동 집계
 *
 * @author 박재익
 *
 */
public class StudyActLogStatistics {
	public static class Days {
		String date;
		int wrongCnt;
		int rightCnt;
		int backViewCnt;
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
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
}
