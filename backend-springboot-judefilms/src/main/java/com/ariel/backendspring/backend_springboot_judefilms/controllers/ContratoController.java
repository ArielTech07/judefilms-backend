package com.ariel.backendspring.backend_springboot_judefilms.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ariel.backendspring.backend_springboot_judefilms.entities.Contrato;
import com.ariel.backendspring.backend_springboot_judefilms.services.ContratoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/contratos")
@RequiredArgsConstructor
public class ContratoController {

    private final ContratoService service;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Contrato> all() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> view(@PathVariable Long id) {
        Optional<Contrato> contratoOptional = service.findById(id);
        if (contratoOptional.isPresent()) {
            return ResponseEntity.ok(contratoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@Valid @RequestBody Contrato contrato, BindingResult result) {
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        Contrato contratoNew = service.save(contrato);
        return ResponseEntity.status(HttpStatus.CREATED).body(contratoNew);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@Valid @RequestBody Contrato contrato, BindingResult result, @PathVariable Long id) {
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        Optional<Contrato> contratoOptional = service.update(id, contrato);
        if (contratoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(contratoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Contrato> contratoOptional = service.deleteById(id);
        if (contratoOptional.isPresent()) {
            return ResponseEntity.ok(contratoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
