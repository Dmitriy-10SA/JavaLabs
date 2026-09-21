package com.andef.javalabs.lab5.controller;

import com.andef.javalabs.lab5.model.MedicalRecordForm;
import com.andef.javalabs.lab5.model.OwnerForm;
import com.andef.javalabs.lab5.model.PetForm;
import com.andef.javalabs.lab5.model.SpecialistForm;
import com.andef.javalabs.lab5.service.ClinicDataViewService;
import com.andef.javalabs.lab5.service.FormOptionsService;
import com.andef.javalabs.lab5.service.Lab5PersistenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/lab5")
@RequiredArgsConstructor
public class Lab5Controller {

    private final FormOptionsService options;
    private final Lab5PersistenceService persistenceService;
    private final ClinicDataViewService clinicDataViewService;

    @GetMapping
    public String index() {
        return "lab5/index";
    }

    @GetMapping("/data")
    public String data(Model model) {
        model.addAttribute("data", clinicDataViewService.getAllData());
        return "lab5/data";
    }

    @GetMapping("/pets/new")
    public String petForm(Model model) {
        model.addAttribute("form", new PetForm());
        model.addAttribute("speciesOptions", options.petSpecies());
        return "lab5/pet-form";
    }

    @PostMapping("/pets")
    public String addPet(@ModelAttribute("form") PetForm form, RedirectAttributes redirectAttributes) {
        persistenceService.savePet(form);
        return success("Питомец «" + form.getName() + "» добавлен", redirectAttributes);
    }

    @GetMapping("/owners/new")
    public String ownerForm(Model model) {
        model.addAttribute("form", new OwnerForm());
        model.addAttribute("cityOptions", options.cities());
        return "lab5/owner-form";
    }

    @PostMapping("/owners")
    public String addOwner(@ModelAttribute("form") OwnerForm form, RedirectAttributes redirectAttributes) {
        persistenceService.saveOwner(form);
        return success("Хозяин «" + form.getFullName() + "» добавлен", redirectAttributes);
    }

    @GetMapping("/medical-records/new")
    public String medicalRecordForm(Model model) {
        model.addAttribute("form", new MedicalRecordForm());
        model.addAttribute("petOptions", options.petsWithoutMedicalRecord());
        return "lab5/medical-record-form";
    }

    @PostMapping("/medical-records")
    public String addMedicalRecord(
            @ModelAttribute("form") MedicalRecordForm form,
            RedirectAttributes redirectAttributes
    ) {
        persistenceService.saveMedicalRecord(form);
        return success("История болезни добавлена: " + form.getDiagnosis(), redirectAttributes);
    }

    @GetMapping("/specialists/new")
    public String specialistForm(Model model) {
        model.addAttribute("form", new SpecialistForm());
        model.addAttribute("specializationOptions", options.specializations());
        return "lab5/specialist-form";
    }

    @PostMapping("/specialists")
    public String addSpecialist(
            @ModelAttribute("form") SpecialistForm form,
            RedirectAttributes redirectAttributes
    ) {
        persistenceService.saveSpecialist(form);
        return success("Специалист «" + form.getFullName() + "» добавлен", redirectAttributes);
    }

    private String success(String message, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/lab5/data";
    }
}
