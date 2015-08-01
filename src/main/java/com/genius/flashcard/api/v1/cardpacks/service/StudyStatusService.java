package com.genius.flashcard.api.v1.cardpacks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.genius.flashcard.api.auth.dto.User;
import com.genius.flashcard.api.v1.cardpacks.dao.StudyStatusDao;
import com.genius.flashcard.api.v1.cardpacks.dto.StudyStatus;
import com.genius.flashcard.api.v1.cardpacks.param.StudyStatusParam;
import com.genius.flashcard.common.enums.StudyStatusCdEnum;


@Service
public class StudyStatusService {
	@Autowired
	StudyStatusDao studyStatusDao;

	public void save(StudyStatusParam studyStatusParam, String cardpackId, String s3Key, String userId, User user) {
		StudyStatus studyStatus = new StudyStatus();
		studyStatus.setUserId(userId);
		studyStatus.setStudyStatusCd(StudyStatusCdEnum.IN_STUDY);
		studyStatus.setWrongCnt(studyStatusParam.getWrongs().length);
		studyStatus.setRightCnt(studyStatusParam.getRights().length);
		studyStatus.setCardpackId(cardpackId);
		studyStatus.setS3Key(s3Key);
		studyStatusDao.save(studyStatus);
	}
}
