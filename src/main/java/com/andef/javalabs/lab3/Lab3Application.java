package com.andef.javalabs.lab3;

import com.andef.javalabs.lab3.model.Book;
import com.andef.javalabs.lab3.model.Chair;
import com.andef.javalabs.lab3.model.Laptop;
import com.andef.javalabs.lab3.service.Warehouse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class Lab3Application {

    public static void main(String[] args) {
        SpringApplication.run(Lab3Application.class, args);
    }

    @Bean
    CommandLineRunner demonstrateAdvice(Warehouse warehouse) {
        return args -> {
            new Book("Clean Code").sendToWarehouse(warehouse);
            new Book("Effective Java").sendToWarehouse(warehouse);
            new Laptop("ThinkPad").sendToWarehouse(warehouse);
            new Chair("green").sendToWarehouse(warehouse);

            System.out.println("-----------------------------------------------------------------------");
            System.out.println(warehouse.withdraw("employee", Laptop.class, 1));
            System.out.println("-----------------------------------------------------------------------");
            System.out.println(warehouse.withdraw("guest", Book.class, 2));
            System.out.println("-----------------------------------------------------------------------");
            System.out.println(warehouse.withdraw("manager", Chair.class, 2));
            System.out.println("-----------------------------------------------------------------------");
            System.out.println(warehouse.withdraw("blocked-user", Book.class, 1));
            System.out.println("-----------------------------------------------------------------------");
        };
    }
}
