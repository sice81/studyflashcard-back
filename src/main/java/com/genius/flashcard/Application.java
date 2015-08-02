package com.genius.flashcard;

import java.sql.SQLException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class Application {
	@Autowired
	UserDao userDao;

	@Value("${app.step}")
	String APP_STEP;

	@Value("${app.db.driver}")
	String APP_DB_DRIVER;

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    public void addData() {
    	System.out.println("app.step = " + APP_STEP);
    	System.out.println("app.db.driver = " + APP_DB_DRIVER);
    }
}
