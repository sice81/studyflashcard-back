package com.genius.flashcard.common.enums;

/**
 * 카드팩 접근권한코드
 * @author Administrator
 *
 */
public enum CardpackAccessCdEnum implements Enumable {
	UNKNOWN(null),

	EMPTY(""),

	/**
	 * 비공개
	 */
	PRIVATE("pr"),

	/**
	 * 내 친구 공개
	 */
	FREIND_ONLY("fo"),

	/**
	 * 모두 공개
	 */
	PUBLIC("pu"),
	;

	String value;

	CardpackAccessCdEnum(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}

	public static CardpackAccessCdEnum parse(String value) {
		CardpackAccessCdEnum result = CardpackAccessCdEnum.UNKNOWN;

		if (value == null) {
			return null;
		}

		for (CardpackAccessCdEnum e : CardpackAccessCdEnum.values()) {
			if (value.equals(e.value())) {
				result = e;
				break;
			}
		}

		return result;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public CardpackAccessCdEnum getEnum(String value) {
		return parse(value);
	}
}
