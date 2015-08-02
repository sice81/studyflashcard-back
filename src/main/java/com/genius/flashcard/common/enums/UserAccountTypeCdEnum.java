package com.genius.flashcard.common.enums;

/**
 * 어카운트 타입
 * @author Administrator
 *
 */
public enum UserAccountTypeCdEnum implements Enumable {
	UNKNOWN(null),
	
	EMPTY(""),
	
	INNER("in"),
	
	FACEBOOK("fb"),
	
	GOOGLE_PLUS("g+"),
	;
	
	String value;
	
	UserAccountTypeCdEnum(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
	
	public static UserAccountTypeCdEnum parse(String value) {
		UserAccountTypeCdEnum result = UserAccountTypeCdEnum.UNKNOWN;
		
		if (value == null) {
			return null;
		}
		
		for (UserAccountTypeCdEnum e : UserAccountTypeCdEnum.values()) {
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
	public UserAccountTypeCdEnum getEnum(String value) {
		return parse(value);
	}
}
