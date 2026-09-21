package com.andef.javalabs.lab5.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class PetForm {
    private String name;
    private String species;
    private String sex;
    private boolean vaccinated;
    private LocalDate birthDate;
}
