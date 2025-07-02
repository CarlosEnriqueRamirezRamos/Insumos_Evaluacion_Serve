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
public class NodoComercial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_NODO")
    private Long id;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "ID_TIPO_NODO")
    private TipoNodo tipoNodo;

    @OneToMany(mappedBy = "nodoRecepcion")
    private List<DetalleTransaccion> detallesRecepcion;

    @OneToMany(mappedBy = "nodoEntrega")
    private List<DetalleTransaccion> detallesEntrega;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoNodo getTipoNodo() {
        return tipoNodo;
    }

    public void setTipoNodo(TipoNodo tipoNodo) {
        this.tipoNodo = tipoNodo;
    }

    public List<DetalleTransaccion> getDetallesRecepcion() {
        return detallesRecepcion;
    }

    public void setDetallesRecepcion(List<DetalleTransaccion> detallesRecepcion) {
        this.detallesRecepcion = detallesRecepcion;
    }

    public List<DetalleTransaccion> getDetallesEntrega() {
        return detallesEntrega;
    }

    public void setDetallesEntrega(List<DetalleTransaccion> detallesEntrega) {
        this.detallesEntrega = detallesEntrega;
    }
    
    
}
