package com.andef.javalabs.lab5.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class MedicalRecordForm {
    private String diagnosis;
    private Long petId;
    private String severity;
    private boolean followUpRequired;
    private String treatment;
    private LocalDate lastVisitDate;
}
