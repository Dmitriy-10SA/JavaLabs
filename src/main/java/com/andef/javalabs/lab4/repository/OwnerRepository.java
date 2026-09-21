package com.andef.javalabs.lab4.repository;

import com.andef.javalabs.lab4.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
