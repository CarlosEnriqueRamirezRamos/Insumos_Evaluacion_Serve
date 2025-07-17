package com.CRamirezEvaluacionContratosPetroleo.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Cantidad {
    
    @Id
    @Column(name = "idcantidad")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int IdCantidad;
    
    @Column(name = "nominadarecepcion")
    private double NominadaRecepcion;
    
    @Column(name = "asignadarecepcion")
    private double AsignadaRecepcion;
    
    @Column(name = "nominadaentrega")
    private double NominadaEntrega;
    
    @Column(name = "asignadaentrega")
    private double AsignadaEntrega;

    public int getIdCantidad() {
        return IdCantidad;
    }

    public void setIdCantidad(int IdCantidad) {
        this.IdCantidad = IdCantidad;
    }

    public double getNominadaRecepcion() {
        return NominadaRecepcion;
    }

    public void setNominadaRecepcion(double NominadaRecepcion) {
        this.NominadaRecepcion = NominadaRecepcion;
    }

    public double getAsignadaRecepcion() {
        return AsignadaRecepcion;
    }

    public void setAsignadaRecepcion(double AsignadaRecepcion) {
        this.AsignadaRecepcion = AsignadaRecepcion;
    }

    public double getNominadaEntrega() {
        return NominadaEntrega;
    }

    public void setNominadaEntrega(double NominadaEntrega) {
        this.NominadaEntrega = NominadaEntrega;
    }

    public double getAsignadaEntrega() {
        return AsignadaEntrega;
    }

    public void setAsignadaEntrega(double AsignadaEntrega) {
        this.AsignadaEntrega = AsignadaEntrega;
    }
    
    
}
