package com.genius.flashcard.api.v1.cardpacks.dao;

import java.util.Date;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.genius.flashcard.api.auth.dto.User;

@Transactional
@Repository
public class UserProfileDao {
	public static final String CACHE = "UserProfileDaoCache";

	@Autowired
	HibernateTemplate hibernateTemplate;

	@CacheEvict(value = CACHE, key = "#user.userId")
	public void update(User user) {
		updateWithoutEvict(user);
	}
	
	public void updateWithoutEvict(User user) {
		if (user.getModifiedDate() == null) {
			user.setModifiedDate(new Date());
		}
		hibernateTemplate.update(user);
	}

	@Cacheable(value=CACHE, key="#userId")
	public User getUser(String userId) {
		return hibernateTemplate.get(User.class, userId);
	}
}
