package com.ariel.backendspring.backend_springboot_judefilms.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ariel.backendspring.backend_springboot_judefilms.entities.GaleriaItem;
import com.ariel.backendspring.backend_springboot_judefilms.repositories.GaleriaRepository;
import com.ariel.backendspring.backend_springboot_judefilms.services.R2StorageService.R2UploadResult;

@Service
public class GaleriaServiceImpl implements GaleriaService {

    private final GaleriaRepository repository;
    private final R2StorageService r2StorageService;

    public GaleriaServiceImpl(GaleriaRepository repository, R2StorageService r2StorageService) {
        this.repository = repository;
        this.r2StorageService = r2StorageService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<GaleriaItem> listarTodas() {
        return repository.findAllByOrderByFechaCreacionDesc();
    }

    @Override
    @Transactional
    public GaleriaItem guardar(MultipartFile file, String categoria) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("El archivo de imagen es obligatorio y no puede estar vacío");
        }

        String contentType = file.getContentType();
        if (contentType != null && !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Solo se permiten archivos de imagen (JPEG, PNG, WEBP)");
        }

        String categoriaLimpia = (categoria != null && !categoria.isBlank())
                ? categoria.trim().toLowerCase()
                : "bodas";

        try {
            R2UploadResult uploadResult = r2StorageService.uploadImage(file);
            GaleriaItem item = new GaleriaItem(categoriaLimpia, uploadResult.url(), uploadResult.key());
            return repository.save(item);
        } catch (IOException e) {
            throw new RuntimeException("Error al subir la imagen al almacenamiento Cloudflare R2: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public Optional<GaleriaItem> eliminar(Long id) {
        Optional<GaleriaItem> galeriaOptional = repository.findById(id);
        galeriaOptional.ifPresent(galeriaDb -> {
            if (galeriaDb.getR2Key() != null && !galeriaDb.getR2Key().isBlank()) {
                try {
                    r2StorageService.deleteFile(galeriaDb.getR2Key());
                } catch (Exception e) {
                    System.err.println("Advertencia al eliminar archivo en R2: " + e.getMessage());
                }
            }
            repository.delete(galeriaDb);
        });
        return galeriaOptional;
    }
}
