package com.genius.flashcard.api.v1.cardpacks.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.genius.flashcard.annotation.CurrentUser;
import com.genius.flashcard.api.auth.dao.UserDao;
import com.genius.flashcard.api.auth.dto.User;
import com.genius.flashcard.api.v1.cardpacks.dao.CardpackDao;
import com.genius.flashcard.api.v1.cardpacks.dao.CardpackStoreDao;
import com.genius.flashcard.api.v1.cardpacks.dto.CardpackStore;

@RestController
@RequestMapping("/api/app/v1")
public class CardpackStoreController {
	Logger logger = Logger.getLogger(this.getClass().getName());

	@Autowired
	UserDao userDao;

	@Autowired
	CardpackDao cardpackDao;

	@Autowired
	CardpackStoreDao cardpackStoreDao;

	@RequestMapping(value = "/store", method = RequestMethod.GET)
	public List<Map<String, Object>> getStudyActStatistics(@CurrentUser User user) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<CardpackStore> listStore = cardpackStoreDao.findByRank(1, 100);

		for (CardpackStore e : listStore) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("rank", e.getRank());
			map.put("cardpackId", e.getCardpackId());
			map.put("title", e.getTitle());
			list.add(map);
		}

		return list;
	}
}
