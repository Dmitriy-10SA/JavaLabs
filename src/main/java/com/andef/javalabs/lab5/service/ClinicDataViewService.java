package com.andef.javalabs.lab5.service;

import com.andef.javalabs.lab4.entity.MedicalRecord;
import com.andef.javalabs.lab4.repository.OwnerRepository;
import com.andef.javalabs.lab4.repository.PetRepository;
import com.andef.javalabs.lab4.repository.SpecialistRepository;
import com.andef.javalabs.lab5.model.ClinicDataView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClinicDataViewService {

    private final PetRepository petRepository;
    private final OwnerRepository ownerRepository;
    private final SpecialistRepository specialistRepository;

    @Transactional(readOnly = true)
    public ClinicDataView getAllData() {
        var pets = petRepository.findAll();

        var petRows = pets.stream()
                .map(pet -> new ClinicDataView.PetRow(
                        pet.getId(),
                        pet.getName(),
                        pet.getSpecies(),
                        pet.getBirthDate(),
                        pet.getSex(),
                        pet.isVaccinated()
                ))
                .toList();

        var ownerRows = ownerRepository.findAll().stream()
                .map(owner -> new ClinicDataView.OwnerRow(
                        owner.getId(),
                        owner.getFullName(),
                        owner.getPhone(),
                        owner.getEmail(),
                        owner.getCity(),
                        owner.getContactMethod(),
                        owner.isEmergencyContact()
                ))
                .toList();

        var medicalRecordRows = pets.stream()
                .filter(pet -> pet.getMedicalRecord() != null)
                .map(pet -> toMedicalRecordRow(pet.getName(), pet.getMedicalRecord()))
                .toList();

        var specialistRows = specialistRepository.findAll().stream()
                .map(specialist -> new ClinicDataView.SpecialistRow(
                        specialist.getId(),
                        specialist.getFullName(),
                        specialist.getPhone(),
                        specialist.getSpecialization(),
                        specialist.getShift(),
                        specialist.isAvailableForEmergency()
                ))
                .toList();

        return new ClinicDataView(petRows, ownerRows, medicalRecordRows, specialistRows);
    }

    private ClinicDataView.MedicalRecordRow toMedicalRecordRow(String petName, MedicalRecord record) {
        return new ClinicDataView.MedicalRecordRow(
                record.getId(),
                petName,
                record.getDiagnosis(),
                record.getTreatment(),
                record.getLastVisitDate(),
                record.getSeverity(),
                record.isFollowUpRequired()
        );
    }
}
