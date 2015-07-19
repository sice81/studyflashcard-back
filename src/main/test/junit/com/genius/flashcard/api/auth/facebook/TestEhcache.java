package junit.com.genius.flashcard.api.auth.facebook;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.genius.flashcard.Application;
import com.genius.flashcard.config.AppConfig;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;

public class TestEhcache {
	AppConfig appconfig = new AppConfig();
	
	Cache cache;
	
	@Before
	public void init() throws NoSuchAlgorithmException, NoSuchProviderException {
		CacheManager cacheManager = appconfig.cacheManager();
		
		CacheConfiguration cacheConfiguration = new CacheConfiguration("token", 10000);
		cacheConfiguration.setOverflowToDisk(true);
		cacheConfiguration.maxElementsOnDisk(10*10000);
		cacheConfiguration.maxMemoryOffHeap("10M"); // 10MB
		cacheConfiguration.setTimeToIdleSeconds(30*60); // 30min
		cacheConfiguration.setTimeToLiveSeconds(30*60);
		cache = new Cache(cacheConfiguration);
		cacheManager.addCache(cache);
	}
	
	@Test
	public void testTokenService() {
		String key = "MTQzNzMwODY1MzE2ODozOTk0NWYzYzhkYTYxODk1ZTQ2YjA2MTI3ZjMzM2VjZWM0MTY1NjZmZWMxYzM1ZGU1ZWNkMGJjYWZkM2Q3ZDA1OjphNmMyNjVlMTZhYTQwZTc4YThkMTUxODExNjdkZTE5NzEwZjhhYWVlMWQyODMwOWRiZjM2ZDEwNDBhNGM1YzFhZWNhNTNkYTJiYmZlMDU2NmY2Yzk0MWFmMjY2ZmM3NGZlM2FlMWVkYTgxNjlkZmVjYzY0ZTg3M2Y5ZDE5OTM5MQ";
		String origin = "skldjfklsdjklfjskldjklf";
		Element e = new Element(key, origin);
		cache.put(e);
		
		e = cache.get(key);
		String obj = (String)e.getObjectValue();
		
		Assert.isTrue(obj.equals(origin));
	}
}
