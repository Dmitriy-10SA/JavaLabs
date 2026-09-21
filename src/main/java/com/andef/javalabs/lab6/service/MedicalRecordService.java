package com.andef.javalabs.lab6.service;

import com.andef.javalabs.lab4.entity.MedicalRecord;
import com.andef.javalabs.lab4.entity.Pet;
import com.andef.javalabs.lab4.repository.MedicalRecordRepository;
import com.andef.javalabs.lab4.repository.PetRepository;
import com.andef.javalabs.lab6.dto.ClinicDtos.MedicalRecordRequest;
import com.andef.javalabs.lab6.dto.ClinicDtos.MedicalRecordResponse;
import com.andef.javalabs.lab6.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final PetRepository petRepository;

    @Transactional(readOnly = true)
    public List<MedicalRecordResponse> getAll() {
        return medicalRecordRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public MedicalRecordResponse getOne(Long id) {
        return toResponse(findRecord(id));
    }

    @Transactional
    public MedicalRecordResponse create(MedicalRecordRequest request) {
        validate(request);
        Pet pet = findPet(request.petId());
        if (pet.getMedicalRecord() != null) {
            throw new IllegalStateException("The pet already has a medical record");
        }

        MedicalRecord record = new MedicalRecord(
                request.diagnosis(), request.treatment(), request.lastVisitDate()
        );
        applyRequest(record, request);
        pet.setMedicalRecord(record);
        petRepository.save(pet);
        return toResponse(record);
    }

    @Transactional
    public MedicalRecordResponse update(Long id, MedicalRecordRequest request) {
        validate(request);
        MedicalRecord record = findRecord(id);
        Pet requestedPet = findPet(request.petId());
        Pet currentPet = record.getPet();

        if (currentPet == null || !currentPet.getId().equals(requestedPet.getId())) {
            if (requestedPet.getMedicalRecord() != null) {
                throw new IllegalStateException("The requested pet already has a medical record");
            }
            if (currentPet != null) {
                currentPet.clearMedicalRecord();
            }
            requestedPet.setMedicalRecord(record);
        }

        record.setDiagnosis(request.diagnosis());
        record.setTreatment(request.treatment());
        record.setLastVisitDate(request.lastVisitDate());
        applyRequest(record, request);
        return toResponse(record);
    }

    @Transactional
    public void delete(Long id) {
        MedicalRecord record = findRecord(id);
        Pet pet = record.getPet();
        if (pet != null) {
            pet.clearMedicalRecord();
            petRepository.save(pet);
        }
        medicalRecordRepository.delete(record);
    }

    private void applyRequest(MedicalRecord record, MedicalRecordRequest request) {
        record.setSeverity(request.severity());
        record.setFollowUpRequired(request.followUpRequired());
    }

    private void validate(MedicalRecordRequest request) {
        requireText(request.diagnosis(), "diagnosis");
        requireText(request.treatment(), "treatment");
        if (request.lastVisitDate() == null || request.petId() == null) {
            throw new IllegalArgumentException("lastVisitDate and petId must be specified");
        }
    }

    private MedicalRecord findRecord(Long id) {
        return medicalRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medical record", id));
    }

    private Pet findPet(Long id) {
        return petRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pet", id));
    }

    private MedicalRecordResponse toResponse(MedicalRecord record) {
        return new MedicalRecordResponse(
                record.getId(), record.getDiagnosis(), record.getTreatment(), record.getLastVisitDate(),
                record.getSeverity(), record.isFollowUpRequired(),
                record.getPet() == null ? null : record.getPet().getId()
        );
    }

    private void requireText(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " must not be blank");
        }
    }
}
