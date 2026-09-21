package com.andef.javalabs.lab1.model;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Getter
@Component("annotationCar")
@Qualifier("annotationCar")
@Scope("singleton")
public class Car {

    private String model;

    public Car() {
        System.out.println("Class Car: constructor");
    }

    @Value("${car.model:Toyota}")
    public void setModel(String model) {
        System.out.println("Class Car: setModel method");
        this.model = model;
    }

    public String drive() {
        return model + " is moving";
    }

    @PostConstruct
    public void init() {
        System.out.println("Class Car: init method");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Class Car: destroy method");
    }
}
