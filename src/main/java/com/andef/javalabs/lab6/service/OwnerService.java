package com.andef.javalabs.lab6.service;

import com.andef.javalabs.lab4.entity.Owner;
import com.andef.javalabs.lab4.entity.Pet;
import com.andef.javalabs.lab4.repository.OwnerRepository;
import com.andef.javalabs.lab6.dto.ClinicDtos.OwnerRequest;
import com.andef.javalabs.lab6.dto.ClinicDtos.OwnerResponse;
import com.andef.javalabs.lab6.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;

    @Transactional(readOnly = true)
    public List<OwnerResponse> getAll() {
        return ownerRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public OwnerResponse getOne(Long id) {
        return toResponse(findOwner(id));
    }

    @Transactional
    public OwnerResponse create(OwnerRequest request) {
        validate(request);
        Owner owner = new Owner(request.fullName(), request.phone(), request.email());
        applyRequest(owner, request);
        return toResponse(ownerRepository.save(owner));
    }

    @Transactional
    public OwnerResponse update(Long id, OwnerRequest request) {
        validate(request);
        Owner owner = findOwner(id);
        owner.setFullName(request.fullName());
        owner.setPhone(request.phone());
        owner.setEmail(request.email());
        applyRequest(owner, request);
        return toResponse(owner);
    }

    @Transactional
    public void delete(Long id) {
        Owner owner = findOwner(id);
        Set.copyOf(owner.getPets()).forEach(pet -> pet.removeOwner(owner));
        ownerRepository.delete(owner);
    }

    private void applyRequest(Owner owner, OwnerRequest request) {
        owner.setCity(request.city());
        owner.setContactMethod(request.contactMethod());
        owner.setEmergencyContact(request.emergencyContact());
    }

    private void validate(OwnerRequest request) {
        requireText(request.fullName(), "fullName");
        requireText(request.phone(), "phone");
        requireText(request.email(), "email");
    }

    private Owner findOwner(Long id) {
        return ownerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Owner", id));
    }

    private OwnerResponse toResponse(Owner owner) {
        return new OwnerResponse(
                owner.getId(), owner.getFullName(), owner.getPhone(), owner.getEmail(), owner.getCity(),
                owner.getContactMethod(), owner.isEmergencyContact(),
                owner.getPets().stream().map(Pet::getId).collect(Collectors.toSet())
        );
    }

    private void requireText(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " must not be blank");
        }
    }
}
