package com.ariel.backendspring.backend_springboot_judefilms.services;

import java.util.List;
import java.util.Optional;
import com.ariel.backendspring.backend_springboot_judefilms.entities.Contrato;

public interface ContratoService {
    List<Contrato> findAll();
    Optional<Contrato> findById(Long id);
    Contrato save(Contrato contrato);
    Optional<Contrato> deleteById(Long id);
    Optional<Contrato> update(Long id, Contrato contrato);
}
