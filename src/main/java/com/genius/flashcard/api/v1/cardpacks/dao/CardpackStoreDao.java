package com.genius.flashcard.api.v1.cardpacks.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.genius.flashcard.api.v1.cardpacks.dto.CardpackStore;

@Transactional
@Repository
public class CardpackStoreDao {
	public static final String CACHE = "CardpackDaoCache";

	@Autowired
	HibernateTemplate hibernateTemplate;

	@SuppressWarnings("unchecked")
	public List<CardpackStore> findByRank(long start, long end) {

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("start", start);
		param.put("end", end);

		String query = String.format("FROM %s c WHERE c.rank >= :start AND c.rank <= :end", CardpackStore.class.getName());
		List<CardpackStore> list = (List<CardpackStore>) hibernateTemplate.findByNamedParam(query, new String[]{"start", "end"}, new Object[]{start, end});
//		List<CardpackStore> list = (List<CardpackStore>) hibernateTemplate.findByValueBean(query, param);

		return list;
	}
}
