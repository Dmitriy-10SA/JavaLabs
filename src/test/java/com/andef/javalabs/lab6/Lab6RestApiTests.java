package com.andef.javalabs.lab6;

import com.andef.javalabs.lab4.entity.Owner;
import com.andef.javalabs.lab4.entity.Pet;
import com.andef.javalabs.lab4.entity.Specialist;
import com.andef.javalabs.lab4.repository.MedicalRecordRepository;
import com.andef.javalabs.lab4.repository.OwnerRepository;
import com.andef.javalabs.lab4.repository.PetRepository;
import com.andef.javalabs.lab4.repository.SpecialistRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Lab6Application.class)
@AutoConfigureMockMvc
@Transactional
class Lab6RestApiTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private SpecialistRepository specialistRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Test
    void crudApiPersistsEntitiesAndRelationships() throws Exception {
        String suffix = UUID.randomUUID().toString();
        String email = suffix + "@example.com";

        mockMvc.perform(post("/api/lab6/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "fullName": "REST Owner",
                                  "phone": "+7-900-100-00-00",
                                  "email": "%s",
                                  "city": "SAMARA",
                                  "contactMethod": "EMAIL",
                                  "emergencyContact": true
                                }
                                """.formatted(email)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value(email));

        Owner owner = ownerRepository.findAll().stream()
                .filter(item -> email.equals(item.getEmail()))
                .findFirst().orElseThrow();

        mockMvc.perform(post("/api/lab6/specialists")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "fullName": "REST Specialist %s",
                                  "specialization": "SURGEON",
                                  "phone": "+7-900-200-00-00",
                                  "shift": "DAY",
                                  "availableForEmergency": true
                                }
                                """.formatted(suffix)))
                .andExpect(status().isCreated());

        Specialist specialist = specialistRepository.findAll().stream()
                .filter(item -> item.getFullName().endsWith(suffix))
                .findFirst().orElseThrow();

        mockMvc.perform(post("/api/lab6/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "REST Pet %s",
                                  "species": "DOG",
                                  "birthDate": "2022-03-14",
                                  "sex": "MALE",
                                  "vaccinated": true,
                                  "specialistId": %d,
                                  "ownerIds": [%d]
                                }
                                """.formatted(suffix, specialist.getId(), owner.getId())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ownerIds[0]").value(owner.getId()))
                .andExpect(jsonPath("$.specialistId").value(specialist.getId()));

        Pet pet = petRepository.findAll().stream()
                .filter(item -> item.getName().endsWith(suffix))
                .findFirst().orElseThrow();

        mockMvc.perform(post("/api/lab6/medical-records")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "diagnosis": "REST diagnosis",
                                  "treatment": "REST treatment",
                                  "lastVisitDate": "2026-09-22",
                                  "severity": "MILD",
                                  "followUpRequired": false,
                                  "petId": %d
                                }
                                """.formatted(pet.getId())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.petId").value(pet.getId()));

        Long recordId = medicalRecordRepository.findAll().stream()
                .filter(item -> "REST diagnosis".equals(item.getDiagnosis()))
                .findFirst().orElseThrow().getId();

        mockMvc.perform(get("/api/lab6/pets/{id}", pet.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.medicalRecordId").value(recordId));

        mockMvc.perform(put("/api/lab6/owners/{id}", owner.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "fullName": "Updated REST Owner",
                                  "phone": "+7-900-100-00-01",
                                  "email": "%s",
                                  "city": "KAZAN",
                                  "contactMethod": "PHONE",
                                  "emergencyContact": false
                                }
                                """.formatted(email)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Updated REST Owner"));

        mockMvc.perform(delete("/api/lab6/medical-records/{id}", recordId))
                .andExpect(status().isNoContent());
        mockMvc.perform(delete("/api/lab6/pets/{id}", pet.getId()))
                .andExpect(status().isNoContent());
        mockMvc.perform(delete("/api/lab6/owners/{id}", owner.getId()))
                .andExpect(status().isNoContent());
        mockMvc.perform(delete("/api/lab6/specialists/{id}", specialist.getId()))
                .andExpect(status().isNoContent());

        assertThat(petRepository.findById(pet.getId())).isEmpty();
    }

    @Test
    void missingResourceReturnsJson404() throws Exception {
        mockMvc.perform(get("/api/lab6/pets/999999999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }
}
