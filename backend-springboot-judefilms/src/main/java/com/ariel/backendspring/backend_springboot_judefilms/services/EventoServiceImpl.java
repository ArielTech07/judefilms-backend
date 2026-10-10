package com.ariel.backendspring.backend_springboot_judefilms.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ariel.backendspring.backend_springboot_judefilms.entities.Evento;
import com.ariel.backendspring.backend_springboot_judefilms.repositories.EventoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventoServiceImpl implements EventoService {

    private final EventoRepository eventoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Evento> findAll() {
        return (List<Evento>) eventoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Evento> findById(Long id) {
        return eventoRepository.findById(id);
    }

    @Override
    @Transactional
    public Evento save(Evento evento) {
        return eventoRepository.save(evento);
    }

    @Override
    @Transactional
    public Optional<Evento> deleteById(Long id) {
        Optional<Evento> eventoOptional = eventoRepository.findById(id);
        eventoOptional.ifPresent(eventoDb -> {
            eventoRepository.delete(eventoDb);
        });
        return eventoOptional;
    }

    @Override
    @Transactional
    public Optional<Evento> update(Long id, Evento evento) {
        Optional<Evento> eventoOptional = eventoRepository.findById(id);
        if (eventoOptional.isPresent()) {
            Evento eventoDb = eventoOptional.get();
            eventoDb.setTipoEvento(evento.getTipoEvento());
            eventoDb.setFecha(evento.getFecha());
            eventoDb.setHora(evento.getHora());
            eventoDb.setPrecioTotal(evento.getPrecioTotal());
            eventoDb.setAdelanto(evento.getAdelanto());
            eventoDb.setNotasAdicionales(evento.getNotasAdicionales());
            return Optional.of(eventoRepository.save(eventoDb));
        }
        return Optional.empty();
    }
}
