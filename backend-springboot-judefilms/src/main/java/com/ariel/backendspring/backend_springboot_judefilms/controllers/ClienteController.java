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

import com.ariel.backendspring.backend_springboot_judefilms.entities.Cliente;
import com.ariel.backendspring.backend_springboot_judefilms.services.ClienteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {
    private final ClienteService service;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Cliente> all() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> view(@PathVariable Long id) {
        Optional<Cliente> clienteOptional = service.findById(id);
        if (clienteOptional.isPresent()) {
            return ResponseEntity.ok(clienteOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Cliente c, BindingResult result) {
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        Cliente clienteNew = service.save(c);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteNew);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Cliente c, BindingResult result, @PathVariable Long id) {
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        Optional<Cliente> clienteOptional = service.update(id, c);
        if (clienteOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(clienteOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Cliente> prodOptional = service.deleteById(id);
        if (prodOptional.isPresent()) {
            return ResponseEntity.ok(prodOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

}