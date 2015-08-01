package com.genius.flashcard.api.v1.cardpacks.dao;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.genius.flashcard.api.v1.cardpacks.dto.StudyActLog;
import com.genius.flashcard.api.v1.cardpacks.dto.StudyStatus;

@Transactional
@Repository
public class StudyActLogDao {
	@Autowired
	HibernateTemplate hibernateTemplate;

	public String insert(StudyActLog studyActLog) {
		Serializable s = hibernateTemplate.save(studyActLog);
		return s.toString();
	}

	public StudyStatus get(String userId) {
		StudyActLog c = new StudyActLog();
		c.setUserId(userId);
		String query = String.format("FROM %s c WHERE c.userId = :userId", StudyActLog.class.getName());
		List<StudyStatus> list = (List<StudyStatus>) hibernateTemplate.findByValueBean(query, c);

		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
}
