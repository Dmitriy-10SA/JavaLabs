package com.andef.javalabs.lab4.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Entity
@Table(name = "medical_record")
@NoArgsConstructor
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String diagnosis;

    @Column(nullable = false, length = 500)
    private String treatment;

    @Column(name = "last_visit_date", nullable = false)
    private LocalDate lastVisitDate;

    @Setter
    private String severity;

    @Setter
    @Column(name = "follow_up_required", nullable = false)
    private boolean followUpRequired;

    @OneToOne(mappedBy = "medicalRecord")
    @Setter
    private Pet pet;

    public MedicalRecord(String diagnosis, String treatment, LocalDate lastVisitDate) {
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.lastVisitDate = lastVisitDate;
    }
}
