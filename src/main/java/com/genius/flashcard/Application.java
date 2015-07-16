package com.genius.flashcard;

import java.sql.SQLException;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws SQLException {
    	Console.main(args);
        SpringApplication.run(Application.class, args);
    }
}
