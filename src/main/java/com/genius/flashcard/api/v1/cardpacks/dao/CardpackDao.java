package com.genius.flashcard.api.v1.cardpacks.dao;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.genius.flashcard.api.v1.cardpacks.dto.Cardpack;

@Transactional
@Repository
public class CardpackDao {
	@Autowired
	HibernateTemplate hibernateTemplate;

	public String save(Cardpack cardpack) {
		Serializable s = hibernateTemplate.save(cardpack);
		return s.toString();
	}

	public void saveOrUpdate(Cardpack cardpack) {
		hibernateTemplate.saveOrUpdate(cardpack);
	}

	public Cardpack get(String cardpackId) {
		return hibernateTemplate.get(Cardpack.class, cardpackId);
	}

	public List<Cardpack> findAll() {
		return hibernateTemplate.loadAll(Cardpack.class);
	}
}