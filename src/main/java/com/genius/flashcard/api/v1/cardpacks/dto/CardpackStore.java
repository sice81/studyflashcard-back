package com.genius.flashcard.api.v1.cardpacks.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 카드팩 스토어
 *
 * @author 박재익
 *
 */
@Entity
@Table(name = "CARDPACK_STORE")
public class CardpackStore {

	/**
	 * 순위
	 */
	@Id
	@Column(name = "RANK", length = 4, unique = true, nullable = false)
	long rank;

	@Column(name = "CARDPACK_ID", length = 20, nullable = false)
	String cardpackId;

	@Column(name = "TITLE", length = 4000, nullable = false)
	String title;

	public long getRank() {
		return rank;
	}

	public void setRank(long rank) {
		this.rank = rank;
	}

	public String getCardpackId() {
		return cardpackId;
	}

	public void setCardpackId(String cardpackId) {
		this.cardpackId = cardpackId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
