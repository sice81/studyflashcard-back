package com.genius.flashcard.hibernate.type;

import com.genius.flashcard.common.enums.CardpackStatusCdEnum;

public class CardpackStatusCdEnumType extends EnumUserType {
	@Override
	public Class returnedClass() {
		return CardpackStatusCdEnum.class;
	}
}
