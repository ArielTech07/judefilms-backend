package com.ariel.backendspring.backend_springboot_judefilms.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "eventos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    @JsonIgnoreProperties({"eventos", "hibernateLazyInitializer", "handler"})
    private Cliente cliente;

    @NotNull
    @Column(name = "tipo_evento", nullable = false, length = 50)
    private String tipoEvento;

    @NotNull
    private LocalDate fecha;

    @NotNull
    private LocalTime hora;

    @Column(name = "precio_total", nullable = false, precision = 10, scale = 2)
    @NotNull
    private BigDecimal precioTotal;

    @Column(precision = 10, scale = 2)
    private BigDecimal adelanto;

    private String notasAdicionales;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Contrato> contratos = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.adelanto == null) {
            this.adelanto = BigDecimal.ZERO;
        }
    }
}
