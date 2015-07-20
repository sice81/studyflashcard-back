package com.genius.flashcard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.genius.flashcard.annotation.CurrentUser;
import com.genius.flashcard.api.auth.dto.User;
import com.genius.flashcard.auth.TokenService;

public class CurrentUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
	
	@Autowired
	TokenService tokenService;
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().equals(User.class) 
				&& parameter.getParameterAnnotation(CurrentUser.class) != null;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		String accessToken = webRequest.getHeader("accessToken");
		
		if (accessToken == null) {
			return WebArgumentResolver.UNRESOLVED;
		}
		
		if (this.supportsParameter(parameter)) {
			User user = tokenService.getUser(accessToken);
			return user;
        } else {
            return WebArgumentResolver.UNRESOLVED;
        }
	}

}
