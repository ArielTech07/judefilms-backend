package com.ariel.backendspring.backend_springboot_judefilms.services;
import java.util.*;

import org.springframework.web.multipart.MultipartFile;

import com.ariel.backendspring.backend_springboot_judefilms.entities.GaleriaItem;

public interface GaleriaService {
    List<GaleriaItem> listarTodas();
    GaleriaItem guardar(MultipartFile file, String categoria);
    Optional<GaleriaItem> eliminar(Long id);
}
