package com.taskflow.userservice;

import com.taskflow.userservice.domain.User;
import com.taskflow.userservice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication

public class UserserviceApplication {

	public static void main(String[] args) {

        SpringApplication.run(UserserviceApplication.class, args);
    }

}
