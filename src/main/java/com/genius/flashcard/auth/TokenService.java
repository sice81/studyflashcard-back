package com.genius.flashcard.auth;


import java.util.logging.Logger;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.token.KeyBasedPersistenceTokenService;
import org.springframework.security.core.token.Token;
import org.springframework.stereotype.Component;

import com.genius.flashcard.api.auth.dto.User;

import ch.qos.logback.core.net.ssl.SecureRandomFactoryBean;

@Component
public class TokenService {
	Logger logger = Logger.getLogger(this.getClass().getName());

	KeyBasedPersistenceTokenService s = new KeyBasedPersistenceTokenService();
	Cache cache;

	@Autowired
	CacheManager cacheManager;

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	@PostConstruct
	public void init() {
		logger.info("init()");

		cache = cacheManager.getCache("TokenServiceCache");

		SecureRandomFactoryBean f = new SecureRandomFactoryBean();
		f.setAlgorithm("SHA1PRNG");

		try {
			s.setSecureRandom(f.createSecureRandom());
		} catch (Exception e) {
			e.printStackTrace();
		}
		s.setServerInteger(3238927);
		s.setServerSecret("44gZAjJeTKHK9kZA4ij6CVAaEXds6j0T");
	}

	public MyToken allocate(User user) {
		MyToken token = new MyToken();

		Token t = s.allocateToken("");
		String accessToken = t.getKey();

		token.setToken(t);
		token.setUser(user);

		cache.put(accessToken, token);

		logger.info(String.format("allocated accessToken = %s", accessToken));

		return token;
	}

	public boolean verify(String accessToken) {
		logger.info(String.format("verify accessToken = %s", accessToken));

		if (accessToken == null) {
			return false;
		}

		ValueWrapper vw = cache.get(accessToken);

		if (vw == null) {
			return false;
		}

		MyToken token = (MyToken) vw.get();

		if (token == null) {
			return false;
		}

		return true;
	}

	public User getUser(String accessToken) {
		logger.info(String.format("verify accessToken = %s", accessToken));

		User result = null;

		if (accessToken == null) {
			return null;
		}

		ValueWrapper vw = cache.get(accessToken);

		if (vw == null) {
			return null;
		}

		MyToken token = (MyToken) vw.get();

		if (token != null) {
			result = token.getUser();
		}

		return result;
	}
}
