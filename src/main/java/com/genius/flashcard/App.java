package com.genius.flashcard;

import java.sql.SQLException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.genius.flashcard.api.auth.dao.UserDao;


@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableCaching
//@SpringBootApplication
public class App {
	@Autowired
	UserDao userDao;

    public static void main(String[] args) throws SQLException {
//    	Console.main();
        SpringApplication.run(App.class, args);
    }

    @PostConstruct
    public void addData() {
//    	System.out.println("addData");
//    	User user = new User();
//    	user.setUserId("sice81");
//    	user.setPassword("1234");
//    	user.setUserAccountType(UserAccountTypeEnum.FACEBOOK);
//    	user.setUserStatus(UserStatusEnum.JUST);
//    	userDao.saveUser(user);
    }
}
