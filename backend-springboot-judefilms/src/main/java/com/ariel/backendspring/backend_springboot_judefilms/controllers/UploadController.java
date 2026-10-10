package com.ariel.backendspring.backend_springboot_judefilms.controllers;


import com.ariel.backendspring.backend_springboot_judefilms.services.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/upload")
public class UploadController {

    private final R2StorageService r2StorageService;

    public UploadController(R2StorageService r2StorageService) {
        this.r2StorageService = r2StorageService;
    }

    @PostMapping
    public ResponseEntity<?> uploadPdf(@RequestParam("file") MultipartFile file) {
        // Validar que no esté vacío
        if (file.isEmpty()) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "El archivo está vacío"));
        }

        // Validar que sea PDF
        String contentType = file.getContentType();
        if (contentType == null || !contentType.equals("application/pdf")) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Solo se permiten archivos PDF"));
        }

        // Limitar a 10MB
        if (file.getSize() > 10 * 1024 * 1024) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "El archivo no puede ser mayor a 10MB"));
        }

        try {
            String url = r2StorageService.uploadPdf(file);
            return ResponseEntity.ok(Map.of(
                "url", url,
                "fileName", file.getOriginalFilename()
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(Map.of("error", "Error al subir el archivo: " + e.getMessage()));
        }
    }
}

