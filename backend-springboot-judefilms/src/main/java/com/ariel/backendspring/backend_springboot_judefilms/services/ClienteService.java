package com.ariel.backendspring.backend_springboot_judefilms.services;

import java.util.*;
import com.ariel.backendspring.backend_springboot_judefilms.entities.Cliente;

public interface ClienteService {
    List<Cliente> findAll();

    Optional<Cliente> findById(Long id);

    Cliente save(Cliente c);

    Optional<Cliente> deleteById(Long id);

    Optional<Cliente> update(Long id, Cliente cliente);
}