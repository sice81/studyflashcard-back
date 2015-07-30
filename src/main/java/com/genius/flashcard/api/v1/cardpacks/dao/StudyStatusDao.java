package com.genius.flashcard.api.v1.cardpacks.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.genius.flashcard.api.v1.cardpacks.dto.StudyStatus;
import com.genius.flashcard.common.enums.StudyStatusCdEnum;

@Transactional
@Repository
public class StudyStatusDao {
	@Autowired
	HibernateTemplate hibernateTemplate;

	public String save(StudyStatus studyStatus) {
		Serializable s = hibernateTemplate.save(studyStatus);
		return s.toString();
	}

	public void saveOrUpdate(StudyStatus studyStatus) {
		hibernateTemplate.saveOrUpdate(studyStatus);
	}

	public StudyStatus get(String userId, String cardpackId) {
		StudyStatus c = new StudyStatus();
		c.setUserId(userId);
		c.setCardpackId(cardpackId);
//		c.setPk(new StudyStatus.PK(userId, cardpackId));
		String query = String.format("FROM %s c WHERE c.userId = :userId AND c.cardpackId = :cardpackId", StudyStatus.class.getName());
		List<StudyStatus> list = (List<StudyStatus>) hibernateTemplate.findByValueBean(query, c);

		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public List<StudyStatus> find(String userId, StudyStatusCdEnum studyStatusCd) {
		StudyStatus c = new StudyStatus();
		c.setUserId(userId);
		c.setStudyStatusCd(studyStatusCd);
		String query = String.format("FROM %s c WHERE c.userId = :userId AND c.studyStatusCd = :studyStatusCd", StudyStatus.class.getName());
		List<StudyStatus> list = (List<StudyStatus>) hibernateTemplate.findByValueBean(query, c);

		return list;
	}

	/**
	 * 유저수 개수
	 * @param cardpackId
	 * @return
	 */
	public long getCountInStudyUserCnt(String cardpackId, StudyStatusCdEnum studyStatusCd) {
		StudyStatus c = new StudyStatus();
		c.setCardpackId(cardpackId);
		c.setStudyStatusCd(studyStatusCd);

		String query = String.format("SELECT COUNT(*) FROM %s c WHERE c.cardpackId = :cardpackId AND c.studyStatusCd = :studyStatusCd", StudyStatus.class.getName());
		List<Long> list = (List<Long>) hibernateTemplate.findByValueBean(query, c);

		return list.get(0);
	}
}
