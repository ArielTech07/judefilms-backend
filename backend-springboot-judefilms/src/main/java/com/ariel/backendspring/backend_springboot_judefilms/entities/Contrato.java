package com.ariel.backendspring.backend_springboot_judefilms.entities;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "contratos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @PrePersist 
    public void prePersist(){
        this.createdAt=LocalDateTime.now();
    }
}
