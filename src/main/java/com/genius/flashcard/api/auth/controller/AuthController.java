package com.genius.flashcard.api.auth.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.genius.flashcard.api.auth.dao.UserDao;
import com.genius.flashcard.api.auth.dto.User;
import com.genius.flashcard.api.auth.service.FacebookValidateService;
import com.genius.flashcard.api.auth.service.FacebookValidateService.FacebookUserResDto;
import com.genius.flashcard.api.v1.cardpacks.service.S3Service;
import com.genius.flashcard.auth.TokenService;
import com.genius.flashcard.common.enums.UserAccountTypeEnum;
import com.genius.flashcard.common.enums.UserStatusEnum;
import com.genius.flashcard.utils.MessageDigestUtil;

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

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public void test(HttpServletResponse resp) throws IOException {
		resp.sendRedirect("https://dev-geniusflashcard-doc-tokyo.s3.amazonaws.com/doc.json");
	}

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

		// 페이스북 인증이 올바른 경우
		if (result != null) {
			User user;

			user = userDao.get(MessageDigestUtil.getMD58(result.getEmail()));

			// 유저 정보가 없는 경우 생성
			if (user == null) {
				Assert.isTrue(result.getId() != null, "Id is null!");
				Assert.isTrue(result.getName() != null, "Name is null!");
				Assert.isTrue(result.getEmail() != null, "Email is null!");

				user = new User();
				user.setUserName(result.getName());
				//user.setUserId(String.format("%s-%s", UserAccountTypeEnum.FACEBOOK.getValue(), userId));
				user.setUserId(MessageDigestUtil.getMD58(result.getEmail()));
				user.setUserEmail(result.getEmail());
				user.setProfilePictureUrl(String.format("https://graph.facebook.com/%s/picture", result.getId()));
				user.setUserAccountType(UserAccountTypeEnum.FACEBOOK);
				user.setUserStatus(UserStatusEnum.ACTIVE);
				user.setExternUserId(userId);
				user.setCreatedDate(new Date());
				user.setLastConnectedDate(new Date());
				userDao.insert(user);
			} else {
				user.setLastConnectedDate(new Date());
				userDao.update(user);
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
