package com.andef.javalabs;

import org.springframework.boot.SpringApplication;

public class TestJavaLabsApplication {

    public static void main(String[] args) {
        SpringApplication.from(JavaLabsApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
