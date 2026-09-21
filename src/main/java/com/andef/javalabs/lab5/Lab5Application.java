package com.andef.javalabs.lab5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.andef.javalabs.lab4.entity")
@EnableJpaRepositories("com.andef.javalabs.lab4.repository")
public class Lab5Application {

    public static void main(String[] args) {
        SpringApplication.run(Lab5Application.class, args);
    }
}
