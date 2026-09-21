package com.andef.javalabs.lab1.model;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Getter
@Component("annotationPet")
@Qualifier("annotationPet")
@Scope("singleton")
public class Pet {

    private String name;

    public Pet() {
        System.out.println("Class Pet: constructor");
    }

    @Value("${pet.name:Barsik}")
    public void setName(String name) {
        System.out.println("Class Pet: setName method");
        this.name = name;
    }

    public String makeSound() {
        return name + " makes a sound";
    }

    @PostConstruct
    public void init() {
        System.out.println("Class Pet: init method");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Class Pet: destroy method");
    }
}
