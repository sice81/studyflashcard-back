package com.genius.flashcard.common.enums;

/**
 * 어카운트 타입
 * @author Administrator
 *
 */
public enum UserTypeEnum implements Enumable {
	UNKNOWN(null),
	
	EMPTY(""),
	
	INNER("in"),
	
	FACEBOOK("fb"),
	
	GOOGLE_PLUS("g+"),
	;
	
	String value;
	
	UserTypeEnum(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
	
	public static UserTypeEnum parse(String value) {
		UserTypeEnum result = UserTypeEnum.UNKNOWN;
		
		if (value == null) {
			return null;
		}
		
		for (UserTypeEnum e : UserTypeEnum.values()) {
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
	public UserTypeEnum getEnum(String value) {
		return parse(value);
	}
}
