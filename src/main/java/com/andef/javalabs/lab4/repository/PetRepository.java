package com.andef.javalabs.lab4.repository;

import com.andef.javalabs.lab4.entity.Pet;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Long> {

    @EntityGraph(attributePaths = {"medicalRecord", "specialist", "owners"})
    Optional<Pet> findDetailedById(Long id);

    List<Pet> findAllByMedicalRecordIsNullOrderByName();
}
