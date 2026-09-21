package com.andef.javalabs.lab2;

import com.andef.javalabs.lab1.model.Car;
import com.andef.javalabs.lab1.model.Employee;
import com.andef.javalabs.lab1.model.Pet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class ManualConfiguration {

    @Bean(initMethod = "init", destroyMethod = "destroy")
    Car car(@Value("${car.model:Toyota}") String model) {
        Car car = new Car();

        car.setModel(model);

        return car;
    }

    @Bean(initMethod = "init", destroyMethod = "destroy")
    Pet pet(@Value("${pet.name:Barsik}") String name) {
        Pet pet = new Pet();

        pet.setName(name);

        return pet;
    }

    @Bean(initMethod = "init", destroyMethod = "destroy")
    Employee employee(
            Car car,
            Pet pet,
            @Value("${employee.name:Alex}") String name,
            @Value("${employee.age:25}") int age
    ) {
        Employee employee = new Employee();

        employee.setCar(car);
        employee.setPet(pet);
        employee.setName(name);
        employee.setAge(age);

        return employee;
    }
}
