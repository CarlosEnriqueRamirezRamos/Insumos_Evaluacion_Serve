package com.Insumos.Evaluacion.Insumos_Evaluacion_Server.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class ZonasTarifa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ZONA")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_TIPO")
    private ZonasTipo tipoZona;

    @OneToMany(mappedBy = "zonaTarifaInyeccion")
    private List<DetalleTransaccion> detallesInyeccion;

    @OneToMany(mappedBy = "zonaTarifaExtraccion")
    private List<DetalleTransaccion> detallesExtraccion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonasTipo getTipoZona() {
        return tipoZona;
    }

    public void setTipoZona(ZonasTipo tipoZona) {
        this.tipoZona = tipoZona;
    }

    public List<DetalleTransaccion> getDetallesInyeccion() {
        return detallesInyeccion;
    }

    public void setDetallesInyeccion(List<DetalleTransaccion> detallesInyeccion) {
        this.detallesInyeccion = detallesInyeccion;
    }

    public List<DetalleTransaccion> getDetallesExtraccion() {
        return detallesExtraccion;
    }

    public void setDetallesExtraccion(List<DetalleTransaccion> detallesExtraccion) {
        this.detallesExtraccion = detallesExtraccion;
    }
    
    
}
