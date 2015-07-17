package com.genius.flashcard.hibernate.type;

import com.genius.flashcard.common.enums.AccountTypeEnum;

public class AccountTypeType extends EnumUserType {
	@Override
	public Class returnedClass() {
		return AccountTypeEnum.class;
	}
}
