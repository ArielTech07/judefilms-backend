package com.ariel.backendspring.backend_springboot_judefilms.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ariel.backendspring.backend_springboot_judefilms.entities.GaleriaItem;

public interface GaleriaRepository extends CrudRepository<GaleriaItem,Long> {
    List<GaleriaItem> findAllByOrderByFechaCreacionDesc();
    List<GaleriaItem> findByCategoriaOrderByFechaCreacion(String categoria);
}
