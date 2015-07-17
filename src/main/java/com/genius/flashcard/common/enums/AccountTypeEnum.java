package com.genius.flashcard.common.enums;

/**
 * 어카운트 타입
 * @author Administrator
 *
 */
public enum AccountTypeEnum implements Enumable {
	UNKNOWN(null),
	
	EMPTY(""),
	
	INNER("in"),
	
	FACEBOOK("fb"),
	
	GOOGLE_PLUS("g+"),
	;
	
	String value;
	
	AccountTypeEnum(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
	
	public static AccountTypeEnum parse(String value) {
		AccountTypeEnum result = AccountTypeEnum.UNKNOWN;
		
		if (value == null) {
			return null;
		}
		
		for (AccountTypeEnum e : AccountTypeEnum.values()) {
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
	public AccountTypeEnum getEnum(String value) {
		return parse(value);
	}
}
