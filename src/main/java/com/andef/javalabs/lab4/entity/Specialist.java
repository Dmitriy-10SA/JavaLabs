package com.andef.javalabs.lab4.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "specialist")
@NoArgsConstructor
public class Specialist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String specialization;

    @Column(nullable = false)
    private String phone;

    @OneToMany(mappedBy = "specialist", fetch = FetchType.LAZY)
    private final List<Pet> pets = new ArrayList<>();

    public Specialist(String fullName, String specialization, String phone) {
        this.fullName = fullName;
        this.specialization = specialization;
        this.phone = phone;
    }

    public void addPet(Pet pet) {
        pets.add(pet);
        pet.setSpecialist(this);
    }
}
