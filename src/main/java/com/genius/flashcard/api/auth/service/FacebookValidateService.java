package com.genius.flashcard.api.auth.service;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FacebookValidateService {
	Logger logger = Logger.getLogger(this.getClass().getName());

	RestTemplate restTemplate = new RestTemplate();
	String URL = "https://graph.facebook.com/me";

	public FacebookUserResDto getUser(String accessToken, String userId) throws Exception {
		FacebookUserResDto result = null;

		Map<String, String> map = new HashMap<String, String>();
		map.put("access_token", accessToken);

		String url = String.format("%s?fields=id,name,email&access_token=%s", URL, accessToken);

		try {
			ResponseEntity<FacebookUserResDto> res = restTemplate.getForEntity(url, FacebookUserResDto.class, map);

			FacebookUserResDto resDto = res.getBody();

			// 성공적인 경우
			if (resDto.error == null) {
				if (userId.equals(res.getBody().getId())) {
					result = res.getBody();
				}
			} else {
				logger.info(String.format("[%s] %s", resDto.error.getCode(), resDto.error.getMessage()));
			}
		} catch (Exception e) {
			logger.log(java.util.logging.Level.WARNING, e.getMessage(), e.getCause());
		}

		return result;
	}

	public static class FacebookUserResDto {
		class Error {
			String message;
			String type;
			String code;

			public String getMessage() {
				return message;
			}
			public void setMessage(String message) {
				this.message = message;
			}
			public String getType() {
				return type;
			}
			public void setType(String type) {
				this.type = type;
			}
			public String getCode() {
				return code;
			}
			public void setCode(String code) {
				this.code = code;
			}
		}

		String name;
		String id;
		String email;
		Error error;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public Error getError() {
			return error;
		}
		public void setError(Error error) {
			this.error = error;
		}
	}
}

