package com.genius.flashcard.api.v1.cardpacks.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.genius.flashcard.api.auth.dto.User;
import com.genius.flashcard.api.v1.cardpacks.dao.CardpackDao;
import com.genius.flashcard.api.v1.cardpacks.dto.Cardpack;
import com.genius.flashcard.api.v1.cardpacks.param.CardpackParam;


@Service
public class CardpackService {
	@Autowired
	CardpackDao cardpackDao;

	/**
	 * 현재접속유저가 해당유저의 카드팩을 생성할 수 있는지 검사
	 * @param userId
	 * @param user
	 * @return
	 */
	public boolean isCanCreate(String userId, User user) {
		return true;
	}

	public Cardpack create(CardpackParam cardpackParam, String userId, User user) {
		Cardpack c = new Cardpack();
		c.setCardpackName(cardpackParam.getCardpackName());
		c.setDocData(cardpackParam.getDocData());
		c.setOwnerUserId(user.getUserId());
		c.setCreatedDate(new Date());

		Cardpack result = null;
		String id = cardpackDao.save(c);
		result = cardpackDao.get(id);
		return result;
	}

	public List<Cardpack> findByUserId(String userId) {
		return cardpackDao.findAll();
	}
}
