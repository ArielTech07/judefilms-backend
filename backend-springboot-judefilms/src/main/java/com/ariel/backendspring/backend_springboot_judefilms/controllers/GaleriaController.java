package com.ariel.backendspring.backend_springboot_judefilms.controllers;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ariel.backendspring.backend_springboot_judefilms.entities.GaleriaItem;
import com.ariel.backendspring.backend_springboot_judefilms.services.GaleriaService;

@RestController
@RequestMapping("/api")
public class GaleriaController {

    private final GaleriaService service;

    public GaleriaController(GaleriaService service) {
        this.service = service;
    }

    @GetMapping("/public/galeria")
    public List<GaleriaItem> mostrarTodos() {
        return service.listarTodas();
    }

    @PostMapping("/galeria")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> agregarItem(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "categoria", defaultValue = "bodas") String categoria) {

        if (file == null || file.isEmpty()) {
            Map<String, String> errors = new HashMap<>();
            errors.put("file", "El archivo de imagen es obligatorio");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        try {
            GaleriaItem itemNew = service.guardar(file, categoria);
            return ResponseEntity.status(HttpStatus.CREATED).body(itemNew);
        } catch (IllegalArgumentException e) {
            Map<String, String> errors = new HashMap<>();
            errors.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        } catch (Exception e) {
            Map<String, String> errors = new HashMap<>();
            errors.put("error", "Error al procesar la imagen: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errors);
        }
    }

    @DeleteMapping("/galeria/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<GaleriaItem> galeriaOptional = service.eliminar(id);
        if (galeriaOptional.isPresent()) {
            return ResponseEntity.ok(galeriaOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
}
