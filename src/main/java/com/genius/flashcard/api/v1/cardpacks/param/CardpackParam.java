package com.genius.flashcard.api.v1.cardpacks.param;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;

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
	 * 사본허용여부
	 */
	@JsonProperty(value = "isAllowCopy")
	boolean isAllowCopy;

	/**
	 * 스토어노출여부
	 */
	@JsonProperty(value = "isExposureStore")
	boolean isExposureStore;

	/**
	 * 카드팩 공개코드
	 */
	String cardpackAccessCd;

	/**
	 * S3 클라우드 키 - 파라미터로 받지 않음... 나중에 다른데로 옮길것...
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

	public boolean isAllowCopy() {
		return isAllowCopy;
	}

	public void setAllowCopy(boolean isAllowCopy) {
		this.isAllowCopy = isAllowCopy;
	}

	public boolean isExposureStore() {
		return isExposureStore;
	}

	public void setExposureStore(boolean isExposureStore) {
		this.isExposureStore = isExposureStore;
	}

	public String getCardpackAccessCd() {
		return cardpackAccessCd;
	}

	public void setCardpackAccessCd(String cardpackAccessCd) {
		this.cardpackAccessCd = cardpackAccessCd;
	}
}
