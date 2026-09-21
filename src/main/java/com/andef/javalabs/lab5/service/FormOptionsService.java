package com.andef.javalabs.lab5.service;

import com.andef.javalabs.lab4.repository.PetRepository;
import com.andef.javalabs.lab5.model.SelectOption;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FormOptionsService {

    private final PetRepository petRepository;

    public List<SelectOption> petSpecies() {
        return List.of(
                new SelectOption("CAT", "Кошка"),
                new SelectOption("DOG", "Собака"),
                new SelectOption("BIRD", "Птица")
        );
    }

    public List<SelectOption> cities() {
        return List.of(
                new SelectOption("SAMARA", "Самара"),
                new SelectOption("MOSCOW", "Москва"),
                new SelectOption("KAZAN", "Казань")
        );
    }

    @Transactional(readOnly = true)
    public List<SelectOption> petsWithoutMedicalRecord() {
        return petRepository.findAllByMedicalRecordIsNullOrderByName().stream()
                .map(pet -> new SelectOption(
                        pet.getId().toString(),
                        pet.getName() + " — " + pet.getSpecies()
                ))
                .toList();
    }

    public List<SelectOption> specializations() {
        return List.of(
                new SelectOption("THERAPIST", "Терапевт"),
                new SelectOption("SURGEON", "Хирург"),
                new SelectOption("DENTIST", "Стоматолог")
        );
    }
}
