package com.genius.flashcard.api.v1.cardpacks.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.genius.flashcard.api.v1.cardpacks.dao.StudyActLogStatisticsDao;
import com.genius.flashcard.api.v1.cardpacks.dto.StudyActLogStatistics;
import com.genius.flashcard.utils.DateHelper;


@Service
public class StudyActLogStatisticsService {

	@Autowired
	StudyActLogStatisticsDao studyActLogStatisticsDao;

	public List<StudyActLogStatistics> findDays(String userId, Date startDate, Date endDate) {
		List<StudyActLogStatistics> result = new ArrayList<StudyActLogStatistics>();
		List<StudyActLogStatistics> list = studyActLogStatisticsDao.findDaysStatistics(userId, startDate, endDate);

		DateHelper index = new DateHelper(startDate);
		DateHelper end = new DateHelper(endDate);

		index.addDate(-1);
		end.addDate(-1);

		while (true) {
			if (index.getTime().getTime() >= end.getTime().getTime()) {
				break;
			}

			index.addDate(1);

			StudyActLogStatistics n = new StudyActLogStatistics();
			n.setDate(index.parse("yyyyMMdd"));

			StudyActLogStatistics s = getByDate(n.getDate(), list);

			if (s != null) {
				n.setBackViewCnt(s.getBackViewCnt());
				n.setRightCnt(s.getRightCnt());
				n.setWrongCnt(s.getWrongCnt());
			}

			result.add(n);
		}

		return result;
	}

	private StudyActLogStatistics getByDate(String date, List<StudyActLogStatistics> src) {
		for (StudyActLogStatistics e : src) {
			if (date.equals(e.getDate())) {
				return e;
			}
		}

		return null;
	}
}
