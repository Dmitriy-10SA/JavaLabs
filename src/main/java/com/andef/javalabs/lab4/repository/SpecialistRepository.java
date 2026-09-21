package com.andef.javalabs.lab4.repository;

import com.andef.javalabs.lab4.entity.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialistRepository extends JpaRepository<Specialist, Long> {
}
