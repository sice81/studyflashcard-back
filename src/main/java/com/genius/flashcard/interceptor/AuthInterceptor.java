package com.genius.flashcard.interceptor;

import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.genius.flashcard.auth.TokenService;

public class AuthInterceptor implements HandlerInterceptor {
	Logger logger = Logger.getLogger(this.getClass().getName());

	@Autowired
	TokenService tokenService;

	String[] allows = new String[] { "/nop", "/nop2", "/api/auth/facebook" };

	String[] allowsRegExp = new String[] { "/api/app/v1/cardpacks/[0-9]+", "/api/app/v1/cardpacks/[0-9]+/doc" };

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String uri = request.getRequestURI();

		// 인증 패스 목록 검사
		for (String i : allows) {
			if (i.equals(uri)) {
				return true;
			}
		}

		for (String regex : allowsRegExp) {
			if (Pattern.matches(regex, uri)) {
				return true;
			}
		}

		String accessToken = request.getParameter("accessToken");
		if (accessToken == null) {
			accessToken = request.getHeader("x-session-token");
		}

		if (tokenService.verify(accessToken) == false) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			return false;
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
