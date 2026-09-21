package com.andef.javalabs.lab4.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@Table(name = "pet")
@NoArgsConstructor
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Setter
    private String name;

    @Column(nullable = false)
    @Setter
    private String species;

    @Column(name = "birth_date", nullable = false)
    @Setter
    private LocalDate birthDate;

    @Setter
    private String sex;

    @Setter
    @Column(nullable = false)
    private boolean vaccinated;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "medical_record_id", unique = true)
    private MedicalRecord medicalRecord;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialist_id")
    private Specialist specialist;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "pet_owner",
            joinColumns = @JoinColumn(name = "pet_id"),
            inverseJoinColumns = @JoinColumn(name = "owner_id")
    )
    private final Set<Owner> owners = new HashSet<>();

    public Pet(String name, String species, LocalDate birthDate) {
        this.name = name;
        this.species = species;
        this.birthDate = birthDate;
    }

    public void setMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
        medicalRecord.setPet(this);
    }

    public void clearMedicalRecord() {
        if (medicalRecord != null) {
            medicalRecord.setPet(null);
            medicalRecord = null;
        }
    }

    public void addOwner(Owner owner) {
        owners.add(owner);
        owner.getPets().add(this);
    }

    public void removeOwner(Owner owner) {
        owners.remove(owner);
        owner.getPets().remove(this);
    }

    public void replaceOwners(Set<Owner> newOwners) {
        Set.copyOf(owners).forEach(this::removeOwner);
        newOwners.forEach(this::addOwner);
    }
}
