package com.andef.javalabs.lab1.model;

import org.springframework.stereotype.Component;

@Component
public class Pet {

    public String makeSound() {
        return "The pet makes a sound";
    }
}
