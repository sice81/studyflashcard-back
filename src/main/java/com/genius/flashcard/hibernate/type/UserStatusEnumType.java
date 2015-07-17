package com.genius.flashcard.hibernate.type;

import com.genius.flashcard.common.enums.UserStatusEnum;

public class UserStatusEnumType extends EnumUserType {
	@Override
	public Class returnedClass() {
		return UserStatusEnum.class;
	}
}
