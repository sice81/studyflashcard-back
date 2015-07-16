package junit.com.genius.flashcard.api.auth.facebook;

import org.junit.Test;

import com.genius.flashcard.api.auth.service.FacebookValidateService;

/**
 * Facebook 서버 사이드 인증 부분
 * @author 박재익
 *
 */
public class TestFacebookValidateService {
	FacebookValidateService facebookValidateService = new FacebookValidateService();
	
	@Test
	public void test() throws Exception {
		String accessToken = "CAANZBB8WYC0oBANY7iWWWUtyVk8M5nePbGGmlX0O8zWHYOzuyIW53bvvfrw1kbaazpObZA5G6PIZAOff8JTwbZCGuamqhMm4cD6vALOOuooMdwKctvFm5Q3oge0LZARk8PCWCZCEySgSMjcobjY5AYT8xGYgGp0oi8QDsImCNze18AQZBx0NSc3Kk2OmWzXLErApqwoNPOknanBZCP15xyu1BTrXfZCE5550ZD";
		String userId = "";
		
		facebookValidateService.validate(accessToken, userId);
	}

}
