package com.ariel.backendspring.backend_springboot_judefilms.services;

import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ariel.backendspring.backend_springboot_judefilms.entities.Cliente;
import com.ariel.backendspring.backend_springboot_judefilms.repositories.ClienteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {
    private final ClienteRepository clienteRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        return (List<Cliente>) clienteRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }

    @Override
    @Transactional
    public Cliente save(Cliente c) {
        return clienteRepository.save(c);
    }

    @Override
    @Transactional
    public Optional<Cliente> deleteById(Long id) {
        Optional<Cliente> clienteOptional=clienteRepository.findById(id);
        clienteOptional.ifPresent(clienteDB ->{
        clienteRepository.delete(clienteDB);
        });
        return clienteOptional;    
    }

    @Override
    @Transactional
    public Optional<Cliente> update(Long id, Cliente c) {
        Optional<Cliente> clieOptional = clienteRepository.findById(id);
        if (clieOptional.isPresent()) {
            Cliente clienteDb = clieOptional.get();
            clienteDb.setNombreCompleto(c.getNombreCompleto());
            clienteDb.setTelefono(c.getTelefono());
            clienteDb.setDireccion(c.getDireccion());
            return Optional.of(clienteRepository.save(clienteDb));
        }
        return Optional.empty();
    }

}