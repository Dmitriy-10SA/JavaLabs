package com.andef.javalabs.lab6.dto;

import java.time.LocalDate;
import java.util.Set;

public final class ClinicDtos {

    private ClinicDtos() {
    }

    public record PetRequest(
            String name,
            String species,
            LocalDate birthDate,
            String sex,
            boolean vaccinated,
            Long specialistId,
            Set<Long> ownerIds
    ) {
    }

    public record PetResponse(
            Long id,
            String name,
            String species,
            LocalDate birthDate,
            String sex,
            boolean vaccinated,
            Long medicalRecordId,
            Long specialistId,
            Set<Long> ownerIds
    ) {
    }

    public record OwnerRequest(
            String fullName,
            String phone,
            String email,
            String city,
            String contactMethod,
            boolean emergencyContact
    ) {
    }

    public record OwnerResponse(
            Long id,
            String fullName,
            String phone,
            String email,
            String city,
            String contactMethod,
            boolean emergencyContact,
            Set<Long> petIds
    ) {
    }

    public record MedicalRecordRequest(
            String diagnosis,
            String treatment,
            LocalDate lastVisitDate,
            String severity,
            boolean followUpRequired,
            Long petId
    ) {
    }

    public record MedicalRecordResponse(
            Long id,
            String diagnosis,
            String treatment,
            LocalDate lastVisitDate,
            String severity,
            boolean followUpRequired,
            Long petId
    ) {
    }

    public record SpecialistRequest(
            String fullName,
            String specialization,
            String phone,
            String shift,
            boolean availableForEmergency
    ) {
    }

    public record SpecialistResponse(
            Long id,
            String fullName,
            String specialization,
            String phone,
            String shift,
            boolean availableForEmergency,
            Set<Long> petIds
    ) {
    }
}
