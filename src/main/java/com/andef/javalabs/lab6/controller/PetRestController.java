package com.andef.javalabs.lab6.controller;

import com.andef.javalabs.lab6.dto.ClinicDtos.PetRequest;
import com.andef.javalabs.lab6.dto.ClinicDtos.PetResponse;
import com.andef.javalabs.lab6.service.PetService;
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
@RequestMapping("/api/lab6/pets")
@RequiredArgsConstructor
public class PetRestController {

    private final PetService service;

    @GetMapping
    public List<PetResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public PetResponse getOne(@PathVariable Long id) {
        return service.getOne(id);
    }

    @PostMapping
    public ResponseEntity<PetResponse> create(@RequestBody PetRequest request) {
        PetResponse result = service.create(request);
        return ResponseEntity.created(URI.create("/api/lab6/pets/" + result.id())).body(result);
    }

    @PutMapping("/{id}")
    public PetResponse update(@PathVariable Long id, @RequestBody PetRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
