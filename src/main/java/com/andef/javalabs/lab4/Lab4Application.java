package com.andef.javalabs.lab4;

import com.andef.javalabs.lab4.service.VeterinaryClinicService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Lab4Application {

    public static void main(String[] args) {
        SpringApplication.run(Lab4Application.class, args);
    }

    @Bean
    CommandLineRunner demonstrateHibernate(VeterinaryClinicService clinicService) {
        return args -> {
            Long petId = clinicService.createDemoData();
            clinicService.demonstrateDefaultLoading(petId);
            clinicService.demonstrateEntityGraphLoading(petId);
        };
    }
}
