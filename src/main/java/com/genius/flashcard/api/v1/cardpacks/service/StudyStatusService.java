package com.genius.flashcard.api.v1.cardpacks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.genius.flashcard.api.auth.dto.User;
import com.genius.flashcard.api.v1.cardpacks.dao.StudyStatusDao;
import com.genius.flashcard.api.v1.cardpacks.dto.StudyStatus;
import com.genius.flashcard.api.v1.cardpacks.param.StudyStatusParam;


@Service
public class StudyStatusService {
	@Autowired
	StudyStatusDao studyStatusDao;

	public void save(StudyStatusParam studyStatusParam, String cardpackId, String s3Key, String userId, User user) {
		StudyStatus studyStatus = new StudyStatus();
//		studyStatus.setUserId(userId);
//		studyStatus.setCardpackId(cardpackId);
		studyStatus.setPk(new StudyStatus.PK(userId, cardpackId));
		studyStatus.setS3Key(s3Key);

		studyStatusDao.saveOrUpdate(studyStatus);
	}
}
