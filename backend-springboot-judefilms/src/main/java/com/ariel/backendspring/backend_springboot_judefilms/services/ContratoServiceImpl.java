package com.ariel.backendspring.backend_springboot_judefilms.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ariel.backendspring.backend_springboot_judefilms.entities.Contrato;
import com.ariel.backendspring.backend_springboot_judefilms.repositories.ContratoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContratoServiceImpl implements ContratoService {

    private final ContratoRepository contratoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Contrato> findAll() {
        return (List<Contrato>) contratoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Contrato> findById(Long id) {
        return contratoRepository.findById(id);
    }

    @Override
    @Transactional
    public Contrato save(Contrato contrato) {
        return contratoRepository.save(contrato);
    }

    @Override
    @Transactional
    public Optional<Contrato> deleteById(Long id) {
        Optional<Contrato> contratoOptional = contratoRepository.findById(id);
        contratoOptional.ifPresent(contratoDb -> {
            contratoRepository.delete(contratoDb);
        });
        return contratoOptional;
    }

    @Override
    @Transactional
    public Optional<Contrato> update(Long id, Contrato contrato) {
        Optional<Contrato> contratoOptional = contratoRepository.findById(id);
        if (contratoOptional.isPresent()) {
            Contrato contratoDb = contratoOptional.get();
            contratoDb.setNombreArchivo(contrato.getNombreArchivo());
            contratoDb.setUrl(contrato.getUrl());
            return Optional.of(contratoRepository.save(contratoDb));
        }
        return Optional.empty();
    }
}
