package com.example.Studentcourse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
@SpringBootApplication

@EnableJpaAuditing
public class StudentcourseApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentcourseApplication.class, args);
	}

}
