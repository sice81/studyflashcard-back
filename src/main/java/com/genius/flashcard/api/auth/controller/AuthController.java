package com.genius.flashcard.api.auth.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.genius.flashcard.annotation.CurrentUser;
import com.genius.flashcard.api.auth.dao.UserDao;
import com.genius.flashcard.api.auth.dto.User;
import com.genius.flashcard.api.auth.service.FacebookValidateService;
import com.genius.flashcard.api.auth.service.FacebookValidateService.FacebookUserResDto;
import com.genius.flashcard.auth.TokenService;
import com.genius.flashcard.common.enums.UserAccountTypeEnum;
import com.genius.flashcard.common.enums.UserStatusEnum;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	Logger logger = Logger.getLogger(this.getClass().getName());

	@Autowired
	UserDao userDao;

	@Autowired
	FacebookValidateService facebookValidateService;

	@Autowired
	TokenService tokenService;

	/**
	 * 페이스북 javascript api로 받은 값을 검증한다.
	 *
	 * @param accessToken 페이스북 access token
	 * @param userId 페이스북 userId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/facebook", method = RequestMethod.POST)
	public Map<String, Object> facebook(@RequestParam String accessToken, @RequestParam String userId, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		FacebookUserResDto result = facebookValidateService.getUser(accessToken, userId);

		if (result != null) {
			User user;

			user = userDao.getUser(String.format("%s-%s", UserAccountTypeEnum.FACEBOOK.getValue(), userId));

			if (user == null) {
				user = new User();
				user.setUserName(result.getName());
				user.setUserId(String.format("%s-%s", UserAccountTypeEnum.FACEBOOK.getValue(), userId));
				user.setUserAccountType(UserAccountTypeEnum.FACEBOOK);
				user.setUserStatus(UserStatusEnum.ACTIVE);
				user.setExternUserId(userId);
				user.setCreatedDate(new Date());
				userDao.saveUser(user);
			}

			map.put("result", true);
			map.put("userId", user.getUserId());
			map.put("accessToken", tokenService.allocate(user).getToken().getKey());
		} else {
			map.put("result", false);
		}

		return map;
	}
}
