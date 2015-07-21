package com.genius.flashcard.api.v1.cardpacks.param;

import java.util.List;

import javax.persistence.Entity;

import com.genius.flashcard.api.v1.cardpacks.vo.Card;

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
}
