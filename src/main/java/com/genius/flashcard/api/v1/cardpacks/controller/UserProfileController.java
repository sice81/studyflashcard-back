package com.genius.flashcard.api.v1.cardpacks.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.genius.flashcard.annotation.CurrentUser;
import com.genius.flashcard.api.auth.dao.UserDao;
import com.genius.flashcard.api.auth.dto.User;
import com.genius.flashcard.api.v1.cardpacks.dao.CardpackDao;
import com.genius.flashcard.api.v1.cardpacks.dao.StudyActLogDao;
import com.genius.flashcard.api.v1.cardpacks.dao.StudyActLogStatisticsDao;
import com.genius.flashcard.api.v1.cardpacks.dao.StudyStatusDao;
import com.genius.flashcard.api.v1.cardpacks.dto.Cardpack;
import com.genius.flashcard.api.v1.cardpacks.dto.StudyActLog;
import com.genius.flashcard.api.v1.cardpacks.dto.StudyActLogStatistics;
import com.genius.flashcard.api.v1.cardpacks.dto.StudyStatus;
import com.genius.flashcard.api.v1.cardpacks.param.CardpackParam;
import com.genius.flashcard.api.v1.cardpacks.param.StudyStatusParam;
import com.genius.flashcard.api.v1.cardpacks.param.StudyStatusParam.StudyActLogParam;
import com.genius.flashcard.api.v1.cardpacks.service.CardpackService;
import com.genius.flashcard.api.v1.cardpacks.service.S3Service;
import com.genius.flashcard.api.v1.cardpacks.service.StudyActLogStatisticsService;
import com.genius.flashcard.api.v1.cardpacks.service.StudyStatusService;
import com.genius.flashcard.api.v1.cardpacks.service.UserProfileService;
import com.genius.flashcard.common.enums.CardpackAccessCdEnum;
import com.genius.flashcard.common.enums.StudyStatusCdEnum;
import com.genius.flashcard.utils.DateHelper;

@RestController
@RequestMapping("/api/app/v1")
public class UserProfileController {
	Logger logger = Logger.getLogger(this.getClass().getName());

	@Autowired
	UserProfileService userProfileService;
	
	@Autowired
	UserDao userDao;

	@RequestMapping(value = "/users/{userId}/profile", method = RequestMethod.GET)
	public User getUserProfile(@PathVariable String userId,
			@CurrentUser User user, HttpServletResponse res) throws Exception {
		Assert.isTrue(userProfileService.isCanGet(userId, user), "You don't have permission!");
		
		return userProfileService.getUser(userId);
	}
	
	@RequestMapping(value = "/users/{userId}/profile", method = RequestMethod.PUT)
	public void putUserProfile(@RequestBody User user,
			@CurrentUser User curUser, HttpServletResponse res) throws Exception {
		Assert.isTrue(user.getUserId().length() > 0, "UserId is empty!");
		Assert.isTrue(user.getUserName().length() > 0, "Name is empty!");
		Assert.isTrue(user.getUserEmail().length() > 0, "Email is empty!");
		Assert.isTrue(userProfileService.isCanModify(user.getUserId(), curUser), "You don't have permission!");
		System.out.println("aaaaaaaaa");
		
		curUser.setUserName(user.getUserName());
		curUser.setUserEmail(user.getUserEmail());
		
		userProfileService.save(curUser);

	}

}
