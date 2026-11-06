package com.ariel.backendspring.backend_springboot_judefilms.entities;

import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "contratos")
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "evento_id", nullable = false)
    @JsonIgnoreProperties({"contratos", "cliente", "hibernateLazyInitializer", "handler"})
    private Evento evento;

    @NotNull 
    private String nombreArchivo;

    @NotNull 
    private String url;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public Contrato() {
    }

    public Contrato(Long id, Evento evento, String nombreArchivo, String url, LocalDateTime createdAt) {
        this.id = id;
        this.evento = evento;
        this.nombreArchivo = nombreArchivo;
        this.url = url;
        this.createdAt = createdAt;
    }

    @PrePersist 
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contrato contrato = (Contrato) o;
        return Objects.equals(id, contrato.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Contrato{" +
                "id=" + id +
                ", nombreArchivo='" + nombreArchivo + '\'' +
                ", url='" + url + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
