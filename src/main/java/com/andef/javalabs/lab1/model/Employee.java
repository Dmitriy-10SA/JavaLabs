package com.andef.javalabs.lab1.model;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Getter
@Component
@Scope("singleton")
public class Employee {

    private Car car;
    private Pet pet;
    private String name;
    private int age;

    public Employee() {
        System.out.println("Class Employee: constructor");
    }

    @Autowired(required = false)
    public void setCar(@Qualifier("annotationCar") Car car) {
        System.out.println("Class Employee: setCar method");
        this.car = car;
    }

    @Autowired(required = false)
    public void setPet(@Qualifier("annotationPet") Pet pet) {
        System.out.println("Class Employee: setPet method");
        this.pet = pet;
    }

    @Value("${employee.name:Alex}")
    public void setName(String name) {
        System.out.println("Class Employee: setName method");
        this.name = name;
    }

    @Value("${employee.age:25}")
    public void setAge(int age) {
        System.out.println("Class Employee: setAge method");
        this.age = age;
    }

    public String introduce() {
        return "My name is " + name + ", I am " + age + " years old";
    }

    public String driveCar() {
        return car.drive();
    }

    public String playWithPet() {
        return pet.makeSound();
    }

    @PostConstruct
    public void init() {
        System.out.println("Class Employee: init method");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Class Employee: destroy method");
    }
}
