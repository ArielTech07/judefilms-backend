package com.ariel.backendspring.backend_springboot_judefilms.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;

@Entity 
@Table(name = "galeria") 
public class GaleriaItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;
    private String categoria;
    private String imageUrl;
    private String r2Key;
    private LocalDate fechaCreacion;

    
    public GaleriaItem() {
    }

    public GaleriaItem(String categoria, String imageUrl, String r2Key) {
        this.categoria = categoria;
        this.imageUrl = imageUrl;
        this.r2Key = r2Key;
        this.fechaCreacion = LocalDate.now();
    }

    public GaleriaItem(Long id, String categoria, String imageUrl, String r2Key, LocalDate fechaCreacion) {
        this.id = id;
        this.categoria = categoria;
        this.imageUrl = imageUrl;
        this.r2Key = r2Key;
        this.fechaCreacion = fechaCreacion;
    }

    @PrePersist
    public void prePersist() {
        if (this.fechaCreacion == null) {
            this.fechaCreacion = LocalDate.now();
        }
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getR2Key() {
        return r2Key;
    }
    public void setR2Key(String r2Key) {
        this.r2Key = r2Key;
    }
    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }
    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    @Override
    public String toString() {
        return "GaleriaItem [id=" + id + ", categoria=" + categoria + ", imageUrl=" + imageUrl + ", r2Key=" + r2Key
                + ", fechaCreacion=" + fechaCreacion + "]";
    }


    
    
}
