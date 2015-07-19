package junit.com.genius.flashcard.api.auth.facebook;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.Assert;

import com.genius.flashcard.api.auth.dto.User;
import com.genius.flashcard.auth.MyToken;
import com.genius.flashcard.auth.TokenService;
import com.genius.flashcard.config.AppConfig;

public class TestTokenService {
	TokenService tokenService;
	
	AppConfig appconfig;
	
	@Before
	public void init() throws NoSuchAlgorithmException, NoSuchProviderException {
		appconfig = new AppConfig();
		
		tokenService = new TokenService();
		tokenService.setCacheManager(appconfig.cacheManager());
		tokenService.init();
	}
	
	@Test
	public void testTokenService() {
		String accessToken;
		
		User user = new User();
		user.setUserId("sice81");
		
		MyToken t = tokenService.allocate(user);
		accessToken = t.getToken().getKey();
		
		Assert.isTrue(tokenService.verify(accessToken));
	}
}
