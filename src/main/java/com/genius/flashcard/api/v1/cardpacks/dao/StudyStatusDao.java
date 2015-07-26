package com.genius.flashcard.api.v1.cardpacks.dao;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.genius.flashcard.api.v1.cardpacks.dto.Cardpack;
import com.genius.flashcard.api.v1.cardpacks.dto.StudyStatus;

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
		String query = String.format("from %s c WHERE c.userId = :userId AND c.cardpackId = :cardpackId", StudyStatus.class.getName());
		List<StudyStatus> list = (List<StudyStatus>) hibernateTemplate.findByValueBean(query, c);

		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
}
