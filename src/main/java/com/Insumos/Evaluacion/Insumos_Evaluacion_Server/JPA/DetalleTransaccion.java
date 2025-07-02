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
public class DetalleTransaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DETALLE")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_NODO_RECEPCION")
    private NodoComercial nodoRecepcion;

    @ManyToOne
    @JoinColumn(name = "ID_NODO_ENTREGA")
    private NodoComercial nodoEntrega;

    @ManyToOne
    @JoinColumn(name = "ID_ZONA_INYECCION")
    private ZonasTarifa zonaTarifaInyeccion;

    @ManyToOne
    @JoinColumn(name = "ID_ZONA_EXTRACCION")
    private ZonasTarifa zonaTarifaExtraccion;

    @OneToMany(mappedBy = "detalleRecepcion")
    private List<Transaccion> transaccionesRecepcion;

    @OneToMany(mappedBy = "detalleEntrega")
    private List<Transaccion> transaccionesEntrega;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NodoComercial getNodoRecepcion() {
        return nodoRecepcion;
    }

    public void setNodoRecepcion(NodoComercial nodoRecepcion) {
        this.nodoRecepcion = nodoRecepcion;
    }

    public NodoComercial getNodoEntrega() {
        return nodoEntrega;
    }

    public void setNodoEntrega(NodoComercial nodoEntrega) {
        this.nodoEntrega = nodoEntrega;
    }

    public ZonasTarifa getZonaTarifaInyeccion() {
        return zonaTarifaInyeccion;
    }

    public void setZonaTarifaInyeccion(ZonasTarifa zonaTarifaInyeccion) {
        this.zonaTarifaInyeccion = zonaTarifaInyeccion;
    }

    public ZonasTarifa getZonaTarifaExtraccion() {
        return zonaTarifaExtraccion;
    }

    public void setZonaTarifaExtraccion(ZonasTarifa zonaTarifaExtraccion) {
        this.zonaTarifaExtraccion = zonaTarifaExtraccion;
    }

    public List<Transaccion> getTransaccionesRecepcion() {
        return transaccionesRecepcion;
    }

    public void setTransaccionesRecepcion(List<Transaccion> transaccionesRecepcion) {
        this.transaccionesRecepcion = transaccionesRecepcion;
    }

    public List<Transaccion> getTransaccionesEntrega() {
        return transaccionesEntrega;
    }

    public void setTransaccionesEntrega(List<Transaccion> transaccionesEntrega) {
        this.transaccionesEntrega = transaccionesEntrega;
    }
    
}