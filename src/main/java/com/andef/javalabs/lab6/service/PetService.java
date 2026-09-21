package com.andef.javalabs.lab6.service;

import com.andef.javalabs.lab4.entity.Owner;
import com.andef.javalabs.lab4.entity.Pet;
import com.andef.javalabs.lab4.entity.Specialist;
import com.andef.javalabs.lab4.repository.OwnerRepository;
import com.andef.javalabs.lab4.repository.PetRepository;
import com.andef.javalabs.lab4.repository.SpecialistRepository;
import com.andef.javalabs.lab6.dto.ClinicDtos.PetRequest;
import com.andef.javalabs.lab6.dto.ClinicDtos.PetResponse;
import com.andef.javalabs.lab6.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final OwnerRepository ownerRepository;
    private final SpecialistRepository specialistRepository;

    @Transactional(readOnly = true)
    public List<PetResponse> getAll() {
        return petRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public PetResponse getOne(Long id) {
        return toResponse(findPet(id));
    }

    @Transactional
    public PetResponse create(PetRequest request) {
        validate(request);
        Pet pet = new Pet(request.name(), request.species(), request.birthDate());
        applyRequest(pet, request);
        return toResponse(petRepository.save(pet));
    }

    @Transactional
    public PetResponse update(Long id, PetRequest request) {
        validate(request);
        Pet pet = findPet(id);
        pet.setName(request.name());
        pet.setSpecies(request.species());
        pet.setBirthDate(request.birthDate());
        applyRequest(pet, request);
        return toResponse(pet);
    }

    @Transactional
    public void delete(Long id) {
        petRepository.delete(findPet(id));
    }

    private void applyRequest(Pet pet, PetRequest request) {
        pet.setSex(request.sex());
        pet.setVaccinated(request.vaccinated());
        pet.setSpecialist(request.specialistId() == null ? null : findSpecialist(request.specialistId()));
        Set<Owner> owners = request.ownerIds() == null
                ? Set.of()
                : request.ownerIds().stream().map(this::findOwner).collect(Collectors.toSet());
        pet.replaceOwners(owners);
    }

    private void validate(PetRequest request) {
        requireText(request.name(), "name");
        requireText(request.species(), "species");
        if (request.birthDate() == null) {
            throw new IllegalArgumentException("birthDate must be specified");
        }
    }

    private Pet findPet(Long id) {
        return petRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pet", id));
    }

    private Owner findOwner(Long id) {
        return ownerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Owner", id));
    }

    private Specialist findSpecialist(Long id) {
        return specialistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Specialist", id));
    }

    private PetResponse toResponse(Pet pet) {
        return new PetResponse(
                pet.getId(), pet.getName(), pet.getSpecies(), pet.getBirthDate(), pet.getSex(),
                pet.isVaccinated(),
                pet.getMedicalRecord() == null ? null : pet.getMedicalRecord().getId(),
                pet.getSpecialist() == null ? null : pet.getSpecialist().getId(),
                pet.getOwners().stream().map(Owner::getId).collect(Collectors.toSet())
        );
    }

    private void requireText(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " must not be blank");
        }
    }
}
