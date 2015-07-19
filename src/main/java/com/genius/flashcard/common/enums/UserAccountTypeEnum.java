package com.genius.flashcard.common.enums;

/**
 * 어카운트 타입
 * @author Administrator
 *
 */
public enum UserAccountTypeEnum implements Enumable {
	UNKNOWN(null),
	
	EMPTY(""),
	
	INNER("in"),
	
	FACEBOOK("fb"),
	
	GOOGLE_PLUS("g+"),
	;
	
	String value;
	
	UserAccountTypeEnum(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
	
	public static UserAccountTypeEnum parse(String value) {
		UserAccountTypeEnum result = UserAccountTypeEnum.UNKNOWN;
		
		if (value == null) {
			return null;
		}
		
		for (UserAccountTypeEnum e : UserAccountTypeEnum.values()) {
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
	public UserAccountTypeEnum getEnum(String value) {
		return parse(value);
	}
}
