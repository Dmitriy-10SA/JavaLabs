package com.andef.javalabs.lab6.service;

import com.andef.javalabs.lab4.entity.Pet;
import com.andef.javalabs.lab4.entity.Specialist;
import com.andef.javalabs.lab4.repository.SpecialistRepository;
import com.andef.javalabs.lab6.dto.ClinicDtos.SpecialistRequest;
import com.andef.javalabs.lab6.dto.ClinicDtos.SpecialistResponse;
import com.andef.javalabs.lab6.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpecialistService {

    private final SpecialistRepository specialistRepository;

    @Transactional(readOnly = true)
    public List<SpecialistResponse> getAll() {
        return specialistRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public SpecialistResponse getOne(Long id) {
        return toResponse(findSpecialist(id));
    }

    @Transactional
    public SpecialistResponse create(SpecialistRequest request) {
        validate(request);
        Specialist specialist = new Specialist(
                request.fullName(), request.specialization(), request.phone()
        );
        applyRequest(specialist, request);
        return toResponse(specialistRepository.save(specialist));
    }

    @Transactional
    public SpecialistResponse update(Long id, SpecialistRequest request) {
        validate(request);
        Specialist specialist = findSpecialist(id);
        specialist.setFullName(request.fullName());
        specialist.setSpecialization(request.specialization());
        specialist.setPhone(request.phone());
        applyRequest(specialist, request);
        return toResponse(specialist);
    }

    @Transactional
    public void delete(Long id) {
        Specialist specialist = findSpecialist(id);
        List.copyOf(specialist.getPets()).forEach(pet -> pet.setSpecialist(null));
        specialistRepository.delete(specialist);
    }

    private void applyRequest(Specialist specialist, SpecialistRequest request) {
        specialist.setShift(request.shift());
        specialist.setAvailableForEmergency(request.availableForEmergency());
    }

    private void validate(SpecialistRequest request) {
        requireText(request.fullName(), "fullName");
        requireText(request.specialization(), "specialization");
        requireText(request.phone(), "phone");
    }

    private Specialist findSpecialist(Long id) {
        return specialistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Specialist", id));
    }

    private SpecialistResponse toResponse(Specialist specialist) {
        return new SpecialistResponse(
                specialist.getId(), specialist.getFullName(), specialist.getSpecialization(),
                specialist.getPhone(), specialist.getShift(), specialist.isAvailableForEmergency(),
                specialist.getPets().stream().map(Pet::getId).collect(Collectors.toSet())
        );
    }

    private void requireText(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " must not be blank");
        }
    }
}
