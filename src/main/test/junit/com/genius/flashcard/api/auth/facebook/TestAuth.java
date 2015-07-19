package junit.com.genius.flashcard.api.auth.facebook;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.token.KeyBasedPersistenceTokenService;
import org.springframework.security.core.token.Token;

import ch.qos.logback.core.net.ssl.SecureRandomFactoryBean;

public class TestAuth {
	KeyBasedPersistenceTokenService s = new KeyBasedPersistenceTokenService();
	
	@Before
	public void init() throws NoSuchAlgorithmException, NoSuchProviderException {
		SecureRandomFactoryBean f = new SecureRandomFactoryBean();
		f.setAlgorithm("SHA1PRNG");
		
		s.setSecureRandom(f.createSecureRandom());
		s.setServerInteger(3238927);
		s.setServerSecret("44gZAjJeTKHK9kZA4ij6CVAaEXds6j0T");
	}
	
	@Test
	public void test() {
		//fail("Not yet implemented");
		Token token = s.allocateToken("hi this is jaeik.hi this is jaeik.hi this is jaeik.hi this is jaeik.hi this is jaeik.");
		Token tokenVerify;
		
		tokenVerify = s.verifyToken(token.getKey());
		token = null;
	}

}
