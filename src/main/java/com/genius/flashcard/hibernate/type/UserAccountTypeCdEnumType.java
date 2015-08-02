package com.genius.flashcard.hibernate.type;

import com.genius.flashcard.common.enums.UserAccountTypeCdEnum;

public class UserAccountTypeCdEnumType extends EnumUserType {
	@Override
	public Class returnedClass() {
		return UserAccountTypeCdEnum.class;
	}
}
