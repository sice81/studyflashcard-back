package com.genius.flashcard.hibernate.type;

import com.genius.flashcard.common.enums.UserTypeEnum;

public class UserTypeEnumType extends EnumUserType {
	@Override
	public Class returnedClass() {
		return UserTypeEnum.class;
	}
}
