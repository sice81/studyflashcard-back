package com.genius.flashcard.auth;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.KeyBasedPersistenceTokenService;
import org.springframework.security.core.token.Token;
import org.springframework.stereotype.Component;

import com.genius.flashcard.api.auth.dto.User;

import ch.qos.logback.core.net.ssl.SecureRandomFactoryBean;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;

@Component
public class TokenService {
	Logger logger = Logger.getLogger(this.getClass().getName());
	
	KeyBasedPersistenceTokenService s = new KeyBasedPersistenceTokenService();
	Ehcache cache;
	
	@Autowired
	CacheManager cacheManager;
	
	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}
	
	@PostConstruct
	public void init() {
		logger.info("init()");
		
		CacheConfiguration cacheConfiguration = new CacheConfiguration("token", 10000);
		cacheConfiguration.setOverflowToDisk(true);
		cacheConfiguration.maxElementsOnDisk(10*10000);
		cacheConfiguration.maxMemoryOffHeap("10M"); // 10MB
		cacheConfiguration.setTimeToIdleSeconds(30*60); // 30min
		cacheConfiguration.setTimeToLiveSeconds(30*60);
		cache = new Cache(cacheConfiguration);
		cacheManager.addCache(cache);
		
		
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
		
		Element e = new Element(accessToken, token);
		cache.put(e);
		
		logger.info(String.format("allocated accessToken = %s", accessToken));
		
		return token;
	}
	
	public boolean verify(String accessToken) {
		logger.info(String.format("verify accessToken = %s", accessToken));
		
		if (accessToken == null) {
			return false;
		}
		
		Element e = cache.get(accessToken);
		
		if (e == null) {
			return false;
		}
		
		MyToken token = (MyToken) e.getObjectValue();
		
		if (token == null) {
			return false;
		}
		
		return true;
	}
}
