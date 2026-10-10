package com.ariel.backendspring.backend_springboot_judefilms.services;

import java.util.List;
import java.util.Optional;
import com.ariel.backendspring.backend_springboot_judefilms.entities.Evento;

public interface EventoService {
    List<Evento> findAll();
    Optional<Evento> findById(Long id);
    Evento save(Evento evento);
    Optional<Evento> deleteById(Long id);
    Optional<Evento> update(Long id, Evento evento);
}
