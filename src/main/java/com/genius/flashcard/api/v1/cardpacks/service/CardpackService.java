package com.genius.flashcard.api.v1.cardpacks.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.genius.flashcard.api.auth.dto.User;
import com.genius.flashcard.api.v1.cardpacks.dao.CardpackDao;
import com.genius.flashcard.api.v1.cardpacks.dto.Cardpack;
import com.genius.flashcard.api.v1.cardpacks.param.CardpackParam;


@Service
public class CardpackService {
	@Autowired
	CardpackDao cardpackDao;

	@Autowired
	MappingJackson2HttpMessageConverter converter;

	/**
	 * 현재접속유저가 해당유저의 카드팩을 생성할 수 있는지 검사
	 * @param userId
	 * @param user
	 * @return
	 */
	public boolean isCanCreate(String userId, User user) {
		return true;
	}

	public boolean isCanGet(String cardpackId, User user) {
		return true;
	}

	@SuppressWarnings("unchecked")
	public int getCardCnt(String json) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		map = converter.getObjectMapper().readValue(json, new TypeReference<HashMap<String,Object>>(){});
		ArrayList<Object> list = (ArrayList<Object>) map.get("cards");
		return list.size();
	}

	public String create(CardpackParam cardpackParam, String userId, User user) throws Exception {
		Cardpack c = new Cardpack();
		c.setCardpackName(cardpackParam.getCardpackName());
//		c.setDocData(cardpackParam.getDocData());
		c.setDocVer("1.0");
		c.setCardCnt(getCardCnt(cardpackParam.getDocData()));
		c.setOwnerUserId(user.getUserId());
		c.setCreatedDate(new Date());
		c.setS3Key(cardpackParam.getS3Key());

		return cardpackDao.create(c);
	}

	public void save(CardpackParam cardpackParam, String cardpackId, String userId, User user) throws Exception {
		Cardpack c = new Cardpack();

		c.setCardpackId(Long.parseLong(cardpackId));
		c.setCardpackName(cardpackParam.getCardpackName());
//		c.setDocData(cardpackParam.getDocData());
		c.setDocVer("1.0");
		c.setCardCnt(getCardCnt(cardpackParam.getDocData()));
		c.setOwnerUserId(user.getUserId());
		c.setCreatedDate(new Date());
		c.setS3Key(cardpackParam.getS3Key());

		cardpackDao.saveOrUpdate(c);
	}

	public List<Cardpack> findByUserId(String userId) {
		return cardpackDao.findByUserId(userId);
	}
}
