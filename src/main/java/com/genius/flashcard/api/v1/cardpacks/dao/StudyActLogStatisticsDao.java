package com.genius.flashcard.api.v1.cardpacks.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.genius.flashcard.api.v1.cardpacks.dto.StudyActLog;
import com.genius.flashcard.api.v1.cardpacks.dto.StudyActLogStatistics;
import com.genius.flashcard.api.v1.cardpacks.dto.StudyStatus;
import com.genius.flashcard.common.enums.StudyStatusCdEnum;

@Transactional
@Repository
public class StudyActLogStatisticsDao {
	@Autowired
	HibernateTemplate hibernateTemplate;

	public StudyActLogStatistics.Days findDays(String userId, Date startDate, Date endDate) {
		StudyActLogStatistics.Days days = null;

		StringBuffer sb = new StringBuffer();
		sb.append("    SELECT ");
		sb.append("    SUM(c.wrongCnt) AS wrongCnt, ");
		sb.append("    SUM(c.rightCnt) AS rightCnt, ");
		sb.append("    SUM(c.backViewCnt) AS backViewCnt ");
		sb.append("    FROM %s c ");
		sb.append("    WHERE c.userId = :userId");
		sb.append("    AND c.createdDate >= :startDate");
		sb.append("    AND c.createdDate <= :endDate");
//		sb.append("    GROUP BY c.userId, c.createdDate");

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("startDate", startDate);
		param.put("endDate", endDate);

		String query = String.format(sb.toString(), StudyActLog.class.getName());
//		List<StudyActLogStatistics.Days> list = (List<StudyActLogStatistics.Days>) hibernateTemplate.findByValueBean(query, param);
		List<StudyActLogStatistics.Days> list = (List<StudyActLogStatistics.Days>) hibernateTemplate.findByNamedParam(query, new String[]{"userId", "startDate", "endDate"}, new Object[]{userId, startDate, endDate});
//		List<Map<String, Object>> list = (List<Map<String, Object>>) hibernateTemplate.findByNamedParam(query, new String[]{"userId", "startDate", "endDate"}, new Object[]{userId, startDate, endDate});

		return days;
	}

	public long getCountInStudyUserCnt(String cardpackId, StudyStatusCdEnum studyStatusCd) {
		StudyStatus c = new StudyStatus();
		c.setCardpackId(cardpackId);
		c.setStatusCd(studyStatusCd);

		String query = String.format("SELECT COUNT(*) FROM %s c WHERE c.cardpackId = :cardpackId AND c.statusCd = :statusCd", StudyStatus.class.getName());
		List<Long> list = (List<Long>) hibernateTemplate.findByValueBean(query, c);

		return list.get(0);
	}
}
