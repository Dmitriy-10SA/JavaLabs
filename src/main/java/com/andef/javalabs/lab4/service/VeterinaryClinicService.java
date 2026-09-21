package com.andef.javalabs.lab4.service;

import com.andef.javalabs.lab4.entity.MedicalRecord;
import com.andef.javalabs.lab4.entity.Owner;
import com.andef.javalabs.lab4.entity.Pet;
import com.andef.javalabs.lab4.entity.Specialist;
import com.andef.javalabs.lab4.repository.OwnerRepository;
import com.andef.javalabs.lab4.repository.PetRepository;
import com.andef.javalabs.lab4.repository.SpecialistRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class VeterinaryClinicService {

    private final PetRepository petRepository;
    private final OwnerRepository ownerRepository;
    private final SpecialistRepository specialistRepository;

    @Transactional
    public Long createDemoData() {
        if (petRepository.count() > 0) {
            return petRepository.findAll().getFirst().getId();
        }

        Specialist specialist = specialistRepository.save(
                new Specialist("Ivan Petrov", "Veterinary surgeon", "+7-900-100-20-30")
        );
        Owner firstOwner = ownerRepository.save(
                new Owner("Anna Sidorova", "+7-900-200-30-40", "anna@example.com")
        );
        Owner secondOwner = ownerRepository.save(
                new Owner("Pavel Sidorov", "+7-900-300-40-50", "pavel@example.com")
        );

        Pet pet = new Pet("Barsik", "Cat", LocalDate.of(2021, 5, 12));
        pet.setMedicalRecord(new MedicalRecord(
                "Seasonal allergy",
                "Antihistamine medication",
                LocalDate.now()
        ));
        specialist.addPet(pet);
        pet.addOwner(firstOwner);
        pet.addOwner(secondOwner);

        return petRepository.save(pet).getId();
    }

    @Transactional(readOnly = true)
    public void demonstrateDefaultLoading(Long petId) {
        Pet pet = petRepository.findById(petId).orElseThrow();

        System.out.println("--- Default loading ---");
        printLoadingState(pet);
        System.out.println("Specialist: " + pet.getSpecialist().getFullName());
        System.out.println("Owners: " + pet.getOwners().size());
        printLoadingState(pet);
    }

    @Transactional(readOnly = true)
    public void demonstrateEntityGraphLoading(Long petId) {
        Pet pet = petRepository.findDetailedById(petId).orElseThrow();

        System.out.println("--- EntityGraph loading ---");
        printLoadingState(pet);
    }

    private void printLoadingState(Pet pet) {
        System.out.println("Medical record initialized (EAGER): "
                + Hibernate.isInitialized(pet.getMedicalRecord()));
        System.out.println("Specialist initialized (LAZY): "
                + Hibernate.isInitialized(pet.getSpecialist()));
        System.out.println("Owners initialized (LAZY): "
                + Hibernate.isInitialized(pet.getOwners()));
    }
}
