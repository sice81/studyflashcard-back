package com.genius.flashcard.common.enums;

/**
 * 유저 상태
 * @author Administrator
 *
 */
public enum UserStatusEnum implements Enumable {
	UNKNOWN(null),
	
	EMPTY(""),
	
	/**
	 * 가입 직후 상태
	 */
	JUST("just"),
	
	/**
	 * 활동 상태
	 */
	ACTIVE("active"),
	
	/**
	 * 휴면 상태 - 일정 기간 활동이 없는 경우
	 */
	DORMANCY("dormancy"),
	
	/**
	 * 이용 금지 상태 - 불법 적인 활동으로 운영자에 의해 금지
	 */
	BANNED("banned"),
	;
	
	String value;
	
	UserStatusEnum(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
	
	public static UserStatusEnum parse(String value) {
		UserStatusEnum result = UserStatusEnum.UNKNOWN;
		
		if (value == null) {
			return null;
		}
		
		for (UserStatusEnum e : UserStatusEnum.values()) {
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
	public UserStatusEnum getEnum(String value) {
		return parse(value);
	}
}
