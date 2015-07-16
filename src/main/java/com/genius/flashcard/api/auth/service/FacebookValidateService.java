package com.genius.flashcard.api.auth.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FacebookValidateService {
	RestTemplate restTemplate = new RestTemplate();
	String URL = "https://graph.facebook.com/me";
	
	public void validate(String accessToken, String userId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("access_token", accessToken);
		
		String url = String.format("%s?access_token=%s", URL, accessToken);
		
//		ResponseEntity<FacebookDto> res = restTemplate.postForEntity(URL, null, FacebookDto.class, map);
		ResponseEntity<FacebookDto> res = restTemplate.getForEntity(url, FacebookDto.class, map);
		
		// 성공적인 경우
		if (res.getBody().error == null) {
			System.out.println("success.");
		} else {
			System.out.println("fail!");
		}
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