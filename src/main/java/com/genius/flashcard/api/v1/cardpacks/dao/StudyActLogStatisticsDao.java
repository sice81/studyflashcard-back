package com.genius.flashcard.api.v1.cardpacks.dao;

import java.util.ArrayList;
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

	public List<StudyActLogStatistics> findDays(String userId, Date startDate, Date endDate) {
		List<StudyActLogStatistics> days = new ArrayList<StudyActLogStatistics>();

		StringBuffer sb = new StringBuffer();
		sb.append("    SELECT ");
		sb.append("    new map ( ");
		sb.append("    		YEAR(c.createdDate) as year, "
				+ "			MONTH(c.createdDate) as month, "
				+ "			DAY(c.createdDate) as date, "
				+ "			SUM(c.wrongCnt) as wrongCnt, "
				+ "			SUM(c.rightCnt) as rightCnt, "
				+ "			SUM(c.backViewCnt) as backViewCnt ");
		sb.append("    ) ");
		sb.append("    FROM %s c ");
		sb.append("    WHERE c.userId = :userId");
		sb.append("    AND c.createdDate >= :startDate");
		sb.append("    AND c.createdDate <= :endDate");
		sb.append("    GROUP BY DAY(c.createdDate)");
		sb.append("    ORDER BY c.createdDate");

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("startDate", startDate);
		param.put("endDate", endDate);

		String query = String.format(sb.toString(), StudyActLog.class.getName());
//		List list = hibernateTemplate.find(query);
		List<Map<String, Object>> list = (List<Map<String, Object>>) hibernateTemplate.findByNamedParam(query, new String[]{"userId", "startDate", "endDate"}, new Object[]{userId, startDate, endDate});
//		List list = hibernateTemplate.findByNamedParam(query, new String[]{"userId", "startDate"}, new Object[]{userId, startDate});

		for (Map<String, Object> e : list) {
			StudyActLogStatistics s = new StudyActLogStatistics();

			s.setDate(String.format("%02d%02d%02d", e.get("year"), e.get("month"), e.get("date")));
			s.setWrongCnt((long)e.get("wrongCnt"));
			s.setRightCnt((long)e.get("rightCnt"));
			s.setBackViewCnt((long)e.get("backViewCnt"));
			days.add(s);
		}

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
