package com.ariel.backendspring.backend_springboot_judefilms.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "eventos")
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
    private List<Contrato> contratos = new ArrayList<>();

    public Evento() {
    }

    public Evento(Long id, Cliente cliente, String tipoEvento, LocalDate fecha, LocalTime hora, BigDecimal precioTotal, BigDecimal adelanto, String notasAdicionales, LocalDateTime createdAt, List<Contrato> contratos) {
        this.id = id;
        this.cliente = cliente;
        this.tipoEvento = tipoEvento;
        this.fecha = fecha;
        this.hora = hora;
        this.precioTotal = precioTotal;
        this.adelanto = adelanto;
        this.notasAdicionales = notasAdicionales;
        this.createdAt = createdAt;
        this.contratos = contratos != null ? contratos : new ArrayList<>();
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.adelanto == null) {
            this.adelanto = BigDecimal.ZERO;
        }
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public BigDecimal getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(BigDecimal precioTotal) {
        this.precioTotal = precioTotal;
    }

    public BigDecimal getAdelanto() {
        return adelanto;
    }

    public void setAdelanto(BigDecimal adelanto) {
        this.adelanto = adelanto;
    }

    public String getNotasAdicionales() {
        return notasAdicionales;
    }

    public void setNotasAdicionales(String notasAdicionales) {
        this.notasAdicionales = notasAdicionales;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Contrato> getContratos() {
        return contratos;
    }

    public void setContratos(List<Contrato> contratos) {
        this.contratos = contratos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Evento evento = (Evento) o;
        return Objects.equals(id, evento.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Evento{" +
                "id=" + id +
                ", tipoEvento='" + tipoEvento + '\'' +
                ", fecha=" + fecha +
                ", hora=" + hora +
                ", precioTotal=" + precioTotal +
                ", adelanto=" + adelanto +
                ", notasAdicionales='" + notasAdicionales + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
