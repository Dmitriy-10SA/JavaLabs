package com.andef.javalabs.lab4.repository;

import com.andef.javalabs.lab4.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
}
