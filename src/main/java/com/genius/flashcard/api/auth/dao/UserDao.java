package com.genius.flashcard.api.auth.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.genius.flashcard.api.auth.dto.User;

@Transactional
@Repository
public class UserDao {
	@Autowired
	HibernateTemplate hibernateTemplate;

	public void insert(User user) {
		hibernateTemplate.save(user);
	}

	public void update(User user) {
		hibernateTemplate.update(user);
	}

	public User get(String userId) {
		return hibernateTemplate.get(User.class, userId);
	}

//	public List<User> findAll() {
//		return hibernateTemplate.loadAll(User.class);
//	}
}
