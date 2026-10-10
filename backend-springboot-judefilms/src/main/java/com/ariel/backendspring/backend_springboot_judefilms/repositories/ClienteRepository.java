package com.ariel.backendspring.backend_springboot_judefilms.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ariel.backendspring.backend_springboot_judefilms.entities.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente,Long> {
    
}
