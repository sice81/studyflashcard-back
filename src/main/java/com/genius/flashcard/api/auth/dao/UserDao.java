package com.genius.flashcard.api.auth.dao;

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
public class UserDao {
	public static final String CACHE = "UserDaoCache";

	@Autowired
	HibernateTemplate hibernateTemplate;

	public void insert(User user) {
		if (user.getCreatedDate() == null) {
			user.setCreatedDate(new Date());
		}

		if (user.getModifiedDate() == null) {
			user.setModifiedDate(new Date());
		}

		hibernateTemplate.save(user);
	}

	@CacheEvict(value=CACHE, key="#user.userId")
	public void update(User user) {
		user.setModifiedDate(new Date());
		hibernateTemplate.update(user);
	}

	@Cacheable(value=CACHE, key="#userId")
	public User get(String userId) {
		return hibernateTemplate.get(User.class, userId);
	}

//	public List<User> findAll() {
//		return hibernateTemplate.loadAll(User.class);
//	}
}
