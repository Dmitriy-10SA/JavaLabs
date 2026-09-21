package com.andef.javalabs.lab6.controller;

import com.andef.javalabs.lab6.dto.ClinicDtos.OwnerRequest;
import com.andef.javalabs.lab6.dto.ClinicDtos.OwnerResponse;
import com.andef.javalabs.lab6.service.OwnerService;
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
@RequestMapping("/api/lab6/owners")
@RequiredArgsConstructor
public class OwnerRestController {

    private final OwnerService service;

    @GetMapping
    public List<OwnerResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public OwnerResponse getOne(@PathVariable Long id) {
        return service.getOne(id);
    }

    @PostMapping
    public ResponseEntity<OwnerResponse> create(@RequestBody OwnerRequest request) {
        OwnerResponse result = service.create(request);
        return ResponseEntity.created(URI.create("/api/lab6/owners/" + result.id())).body(result);
    }

    @PutMapping("/{id}")
    public OwnerResponse update(@PathVariable Long id, @RequestBody OwnerRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
