package com.genius.flashcard.hibernate.type;

import com.genius.flashcard.common.enums.CardpackAccessCdEnum;

public class CardpackAccessCdEnumType extends EnumUserType {
	@Override
	public Class returnedClass() {
		return CardpackAccessCdEnum.class;
	}
}
