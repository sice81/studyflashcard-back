package junit.com.genius.flashcard.api.auth.facebook;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.core.token.KeyBasedPersistenceTokenService;
import org.springframework.security.core.token.Token;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.genius.flashcard.Application;
import com.genius.flashcard.api.auth.dto.User;
import com.genius.flashcard.auth.TokenService;

import ch.qos.logback.core.net.ssl.SecureRandomFactoryBean;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)   // 2
@IntegrationTest("server.port:0")   // 4
public class TestAuth {
	KeyBasedPersistenceTokenService s = new KeyBasedPersistenceTokenService();
	
	@Autowired
	TokenService tokenService;
	
	@Before
	public void init() throws NoSuchAlgorithmException, NoSuchProviderException {
		SecureRandomFactoryBean f = new SecureRandomFactoryBean();
		f.setAlgorithm("SHA1PRNG");
		
		s.setSecureRandom(f.createSecureRandom());
		s.setServerInteger(3238927);
		s.setServerSecret("44gZAjJeTKHK9kZA4ij6CVAaEXds6j0T");
	}
	
//	@Test
	public void test() {
		//fail("Not yet implemented");
		Token token = s.allocateToken("hi this is jaeik.hi this is jaeik.hi this is jaeik.hi this is jaeik.hi this is jaeik.");
		Token tokenVerify;
		
		tokenVerify = s.verifyToken(token.getKey());
		token = null;
	}

	@Test
	public void testTokenService() {
		User user = new User();
		tokenService.allocate(user);
	}
}
