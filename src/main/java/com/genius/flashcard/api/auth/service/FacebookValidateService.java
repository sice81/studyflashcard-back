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
	
	public boolean validate(String accessToken, String userId) throws Exception {
		boolean result = false;
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("access_token", accessToken);
		
		String url = String.format("%s?access_token=%s", URL, accessToken);
		
//		ResponseEntity<FacebookDto> res = restTemplate.postForEntity(URL, null, FacebookDto.class, map);
		try {
			ResponseEntity<FacebookDto> res = restTemplate.getForEntity(url, FacebookDto.class, map);
			
			// 성공적인 경우
			if (res.getBody().error == null) {
				if (userId.equals(res.getBody().getId())) {
					result = true;
				}
			}
		} catch (Exception e) {
			// TODO 로그 좀 정리할 것... 스택트레이스 안 나옴...
			logger.log(java.util.logging.Level.WARNING, e.getMessage(), e.getCause());
		}
		
		return result;
	}
}

class FacebookDto {
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
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
}