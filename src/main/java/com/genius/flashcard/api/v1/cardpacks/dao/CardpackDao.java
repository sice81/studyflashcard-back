package com.genius.flashcard.api.v1.cardpacks.dao;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.genius.flashcard.api.v1.cardpacks.dto.Cardpack;

@Transactional
@Repository
public class CardpackDao {
	public static final String CACHE = "CardpackDaoCache";

	@Autowired
	HibernateTemplate hibernateTemplate;

	public String create(Cardpack cardpack) {
		Serializable s = hibernateTemplate.save(cardpack);
		return s.toString();
	}

	@CacheEvict(value=CACHE, key="#cardpack.cardpackId")
	public void saveOrUpdate(Cardpack cardpack) {
		hibernateTemplate.saveOrUpdate(cardpack);
	}

	@Cacheable(value=CACHE, key="#cardpackId")
	public Cardpack get(String cardpackId) {
		return hibernateTemplate.get(Cardpack.class, cardpackId);
	}

	@SuppressWarnings("unchecked")
	public List<Cardpack> findByUserId(String userId) {
		Cardpack c = new Cardpack();
		c.setOwnerUserId(userId);
		String query = String.format("from %s c WHERE c.ownerUserId = :ownerUserId", Cardpack.class.getName());
		return (List<Cardpack>) hibernateTemplate.findByValueBean(query, c);
	}
}
