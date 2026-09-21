package com.andef.javalabs.lab6.controller;

import com.andef.javalabs.lab6.dto.ClinicDtos.SpecialistRequest;
import com.andef.javalabs.lab6.dto.ClinicDtos.SpecialistResponse;
import com.andef.javalabs.lab6.service.SpecialistService;
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
@RequestMapping("/api/lab6/specialists")
@RequiredArgsConstructor
public class SpecialistRestController {

    private final SpecialistService service;

    @GetMapping
    public List<SpecialistResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public SpecialistResponse getOne(@PathVariable Long id) {
        return service.getOne(id);
    }

    @PostMapping
    public ResponseEntity<SpecialistResponse> create(@RequestBody SpecialistRequest request) {
        SpecialistResponse result = service.create(request);
        return ResponseEntity.created(URI.create("/api/lab6/specialists/" + result.id())).body(result);
    }

    @PutMapping("/{id}")
    public SpecialistResponse update(@PathVariable Long id, @RequestBody SpecialistRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
