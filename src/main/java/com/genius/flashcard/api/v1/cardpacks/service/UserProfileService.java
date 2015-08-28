package com.genius.flashcard.api.v1.cardpacks.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.genius.flashcard.api.auth.dto.User;
import com.genius.flashcard.api.v1.cardpacks.dao.CardpackDao;
import com.genius.flashcard.api.v1.cardpacks.dao.UserProfileDao;
import com.genius.flashcard.api.v1.cardpacks.dto.Cardpack;
import com.genius.flashcard.api.v1.cardpacks.param.CardpackParam;
import com.genius.flashcard.common.enums.CardpackAccessCdEnum;
import com.genius.flashcard.common.enums.CardpackStatusCdEnum;


@Service
public class UserProfileService {

	@Autowired
	MappingJackson2HttpMessageConverter converter;
	
	@Autowired
	UserProfileDao userProfileDao;
	
	@Autowired
	HibernateTemplate hibernateTemplate;

	/**
	 * 현재접속유저가 해당유저의 카드팩을 생성할 수 있는지 검사
	 * @param userId
	 * @param user
	 * @return
	 */

	public boolean isCanGet(String userId, User user) {
		if (!userId.equals(user.getUserId())) {
			return false;
		}
		return true;
	}
	
	public boolean isCanModify(String userId, User user) {
		if (!userId.equals(user.getUserId())) {
			return false;
		}

		return true;
	}
	
	public void save(User user) throws Exception {
		userProfileDao.update(user);
	}
	
	public User getUser(String userId) {
		return userProfileDao.getUser(userId);
	}

}
