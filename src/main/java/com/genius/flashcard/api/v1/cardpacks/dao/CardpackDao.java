package com.genius.flashcard.api.v1.cardpacks.dao;

import java.io.Serializable;
import java.util.Date;
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

	public String insert(Cardpack cardpack) {
		if (cardpack.getCreatedDate() == null) {
			cardpack.setCreatedDate(new Date());
		}

		if (cardpack.getModifiedDate() == null) {
			cardpack.setModifiedDate(new Date());
		}

		Serializable s = hibernateTemplate.save(cardpack);
		return s.toString();
	}

	@CacheEvict(value = CACHE, key = "#cardpack.cardpackId")
	public void update(Cardpack cardpack) {
		updateWithoutEvict(cardpack);
	}

	public void updateWithoutEvict(Cardpack cardpack) {
		if (cardpack.getModifiedDate() == null) {
			cardpack.setModifiedDate(new Date());
		}
		hibernateTemplate.update(cardpack);
	}

	@Cacheable(value = CACHE, key = "#cardpackId")
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
