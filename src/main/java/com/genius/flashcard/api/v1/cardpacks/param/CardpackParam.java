package com.genius.flashcard.api.v1.cardpacks.param;

import javax.persistence.Entity;

@Entity
public class CardpackParam {
	/**
	 * 카드팩명
	 */
	String cardpackName;

	/**
	 * 문서데이터
	 */
	String docData;

	/**
	 * S3 클라우드 키
	 */
	String s3Key;

	public String getCardpackName() {
		return cardpackName;
	}

	public void setCardpackName(String cardpackName) {
		this.cardpackName = cardpackName;
	}

	public String getDocData() {
		return docData;
	}

	public void setDocData(String docData) {
		this.docData = docData;
	}

	public String getS3Key() {
		return s3Key;
	}

	public void setS3Key(String s3Key) {
		this.s3Key = s3Key;
	}

}
