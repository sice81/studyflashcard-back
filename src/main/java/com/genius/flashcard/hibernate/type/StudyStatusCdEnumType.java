package com.genius.flashcard.hibernate.type;

import com.genius.flashcard.common.enums.StudyStatusCdEnum;

public class StudyStatusCdEnumType extends EnumUserType {
	@Override
	public Class returnedClass() {
		return StudyStatusCdEnum.class;
	}
}
