package com.allyoucanexercise.back_end;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExerciseApplication {

	private static final Logger log = LoggerFactory.getLogger(ExerciseApplication.class)

	public static void main(String[] args) {
		SpringApplication.run(ExerciseApplication.class, args);
		log.info("Your application has started!");
	}

}
