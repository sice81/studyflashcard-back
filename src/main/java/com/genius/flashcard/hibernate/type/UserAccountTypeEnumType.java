package com.genius.flashcard.hibernate.type;

import com.genius.flashcard.common.enums.UserAccountTypeEnum;

public class UserAccountTypeEnumType extends EnumUserType {
	@Override
	public Class returnedClass() {
		return UserAccountTypeEnum.class;
	}
}
