package com.andef.javalabs.lab1.model;

import org.springframework.stereotype.Component;

@Component
public class Car {

    public String drive() {
        return "The car is moving";
    }
}
