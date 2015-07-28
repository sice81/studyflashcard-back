package com.genius.flashcard.common.enums;

/**
 * 학습상태코드
 * @author Administrator
 *
 */
public enum StudyStatusCdEnum implements Enumable {
	UNKNOWN(null),

	EMPTY(""),

	/**
	 * 학습중
	 */
	IN_STUDY("is"),

	/**
	 * 학습종료 - 중간에 포기한 경우 (메인화면에서 종료)
	 */
	END("end"),

	/**
	 * 학습완료 - 모두 외워서 학습완료한 경우
	 */
	COMPLETE("co"),
	;

	String value;

	StudyStatusCdEnum(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}

	public static StudyStatusCdEnum parse(String value) {
		StudyStatusCdEnum result = StudyStatusCdEnum.UNKNOWN;

		if (value == null) {
			return null;
		}

		for (StudyStatusCdEnum e : StudyStatusCdEnum.values()) {
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
	public StudyStatusCdEnum getEnum(String value) {
		return parse(value);
	}
}
