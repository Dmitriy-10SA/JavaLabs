package com.andef.javalabs.lab5.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SpecialistForm {
    private String fullName;
    private String specialization;
    private String shift;
    private boolean availableForEmergency;
    private String phone;
}
