package com.genius.flashcard.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {
	Calendar cal;

	public DateHelper() {
		cal = Calendar.getInstance();
	}

	public DateHelper(Date date) {
		this();
		cal.setTime(date);
	}

	public DateHelper(Calendar calendar) {
		this.cal = calendar;
	}

	/**
	 * 형식은 'yyyyMMdd'
	 * @param dateString
	 * @throws ParseException
	 */
	public DateHelper(String dateString) throws ParseException {
		this();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = sdf.parse(dateString);
		cal.setTime(date);
	}

	/**
	 * 날짜를 더한다.
	 * @param amount 더할 날짜 수, 음수인 경우 이전날짜로...
	 * @return
	 */
	public DateHelper addDate(int amount) {
		cal.add(Calendar.DATE, amount);
		return this;
	}

	public int getYear() {
		return cal.get(Calendar.YEAR);
	}

	public int getMonth() {
		return cal.get(Calendar.MONTH) + 1;
	}

	public int getDate() {
		return cal.get(Calendar.DATE);
	}

	public Date getTime() {
		return cal.getTime();
	}

	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		return sdf.format(getTime());
	}
}