package com.andef.javalabs.lab5.service;

import com.andef.javalabs.lab4.entity.MedicalRecord;
import com.andef.javalabs.lab4.entity.Owner;
import com.andef.javalabs.lab4.entity.Pet;
import com.andef.javalabs.lab4.entity.Specialist;
import com.andef.javalabs.lab4.repository.OwnerRepository;
import com.andef.javalabs.lab4.repository.PetRepository;
import com.andef.javalabs.lab4.repository.SpecialistRepository;
import com.andef.javalabs.lab5.model.MedicalRecordForm;
import com.andef.javalabs.lab5.model.OwnerForm;
import com.andef.javalabs.lab5.model.PetForm;
import com.andef.javalabs.lab5.model.SpecialistForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class Lab5PersistenceService {

    private final PetRepository petRepository;
    private final OwnerRepository ownerRepository;
    private final SpecialistRepository specialistRepository;

    @Transactional
    public Pet savePet(PetForm form) {
        Pet pet = new Pet(form.getName(), form.getSpecies(), form.getBirthDate());
        pet.setSex(form.getSex());
        pet.setVaccinated(form.isVaccinated());
        return petRepository.save(pet);
    }

    @Transactional
    public Owner saveOwner(OwnerForm form) {
        Owner owner = new Owner(form.getFullName(), form.getPhone(), form.getEmail());
        owner.setCity(form.getCity());
        owner.setContactMethod(form.getContactMethod());
        owner.setEmergencyContact(form.isEmergencyContact());
        return ownerRepository.save(owner);
    }

    @Transactional
    public MedicalRecord saveMedicalRecord(MedicalRecordForm form) {
        Pet pet = petRepository.findById(form.getPetId())
                .orElseThrow(() -> new IllegalArgumentException("Питомец не найден"));
        if (pet.getMedicalRecord() != null) {
            throw new IllegalStateException("У питомца уже есть история болезни");
        }

        MedicalRecord record = new MedicalRecord(
                form.getDiagnosis(),
                form.getTreatment(),
                form.getLastVisitDate()
        );
        record.setSeverity(form.getSeverity());
        record.setFollowUpRequired(form.isFollowUpRequired());
        pet.setMedicalRecord(record);
        petRepository.save(pet);
        return record;
    }

    @Transactional
    public Specialist saveSpecialist(SpecialistForm form) {
        Specialist specialist = new Specialist(
                form.getFullName(),
                form.getSpecialization(),
                form.getPhone()
        );
        specialist.setShift(form.getShift());
        specialist.setAvailableForEmergency(form.isAvailableForEmergency());
        return specialistRepository.save(specialist);
    }
}
