package com.ariel.backendspring.backend_springboot_judefilms.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ariel.backendspring.backend_springboot_judefilms.entities.Evento;

public interface  EventoRepository extends CrudRepository<Evento,Long>{
    
}
