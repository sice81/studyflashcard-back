package com.genius.flashcard.common.enums;

/**
 * 카드팩 상태코드
 * @author Administrator
 *
 */
public enum CardpackStatusCdEnum implements Enumable {
	UNKNOWN(null),

	EMPTY(""),

	/**
	 * 작성중
	 */
	IN_PROGRESS("i"),

	/**
	 * 서비스중
	 */
	ACTIVE("a"),

	/**
	 * 정지 (작성자 등에 의해)
	 */
	STOP("s"),

	/**
	 * 휴면 상태 - 오랜기간 액세스가 없고 삭제 예정 이전 상태
	 */
	DORMANCY("d"),

	/**
	 * 이용 금지 상태
	 */
	BANNED("b"),
	;

	String value;

	CardpackStatusCdEnum(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}

	public static CardpackStatusCdEnum parse(String value) {
		CardpackStatusCdEnum result = CardpackStatusCdEnum.UNKNOWN;

		if (value == null) {
			return null;
		}

		for (CardpackStatusCdEnum e : CardpackStatusCdEnum.values()) {
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
	public CardpackStatusCdEnum getEnum(String value) {
		return parse(value);
	}
}
