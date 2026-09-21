package com.andef.javalabs.lab5.model;

import java.time.LocalDate;
import java.util.List;

public record ClinicDataView(
        List<PetRow> pets,
        List<OwnerRow> owners,
        List<MedicalRecordRow> medicalRecords,
        List<SpecialistRow> specialists
) {
    public record PetRow(
            Long id,
            String name,
            String species,
            LocalDate birthDate,
            String sex,
            boolean vaccinated
    ) {
    }

    public record OwnerRow(
            Long id,
            String fullName,
            String phone,
            String email,
            String city,
            String contactMethod,
            boolean emergencyContact
    ) {
    }

    public record MedicalRecordRow(
            Long id,
            String petName,
            String diagnosis,
            String treatment,
            LocalDate lastVisitDate,
            String severity,
            boolean followUpRequired
    ) {
    }

    public record SpecialistRow(
            Long id,
            String fullName,
            String phone,
            String specialization,
            String shift,
            boolean availableForEmergency
    ) {
    }
}
