package com.andef.javalabs.lab1.model;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class Employee {

    private final Car car;
    private final Pet pet;
    private final String name;
    private final int age;

    public Employee(
            Car car,
            Pet pet,
            @Value("${employee.name:Alex}") String name,
            @Value("${employee.age:25}") int age
    ) {
        this.car = car;
        this.pet = pet;
        this.name = name;
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
}
