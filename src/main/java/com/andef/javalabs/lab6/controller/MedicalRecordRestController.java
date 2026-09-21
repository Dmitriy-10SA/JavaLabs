package com.andef.javalabs.lab6.controller;

import com.andef.javalabs.lab6.dto.ClinicDtos.MedicalRecordRequest;
import com.andef.javalabs.lab6.dto.ClinicDtos.MedicalRecordResponse;
import com.andef.javalabs.lab6.service.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/lab6/medical-records")
@RequiredArgsConstructor
public class MedicalRecordRestController {

    private final MedicalRecordService service;

    @GetMapping
    public List<MedicalRecordResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public MedicalRecordResponse getOne(@PathVariable Long id) {
        return service.getOne(id);
    }

    @PostMapping
    public ResponseEntity<MedicalRecordResponse> create(@RequestBody MedicalRecordRequest request) {
        MedicalRecordResponse result = service.create(request);
        return ResponseEntity.created(URI.create("/api/lab6/medical-records/" + result.id())).body(result);
    }

    @PutMapping("/{id}")
    public MedicalRecordResponse update(@PathVariable Long id, @RequestBody MedicalRecordRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
