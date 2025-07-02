package com.Insumos.Evaluacion.Insumos_Evaluacion_Server.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class ZonasTipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TIPO")
    private Long id;

    @Column(name = "TIPO", nullable = false)
    private String tipo;

    @OneToMany(mappedBy = "tipoZona")
    private List<ZonasTarifa> zonas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<ZonasTarifa> getZonas() {
        return zonas;
    }

    public void setZonas(List<ZonasTarifa> zonas) {
        this.zonas = zonas;
    }
    
}
