
package com.CRamirezEvaluacionContratosPetroleo.JPA;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.Date;
import java.util.List;

@Entity
public class Transaccion {
    
    @Id
    @Column(name = "idtransaccion")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Idtransaccion;
    
    @Column(name = "fecharegistro")
    private Date FechaRegistro;
    
    @JoinColumn(name = "idcontrato")
    @ManyToOne(cascade = CascadeType.PERSIST)
    public Contrato contrato;
    
    @JoinColumn(name = "idnodorecepcion")
    @ManyToOne(cascade = CascadeType.PERSIST)
    public NodoRecepcion nodoRecepcion;
    
    @JoinColumn(name="idnodoentrega")
    @ManyToOne(cascade = CascadeType.PERSIST)
    public NodoEntrega nodoEntrega;
    
    @JoinColumn(name = "zonainyeccion")
    @ManyToOne(cascade = CascadeType.PERSIST)
    public Zona zonaInyeccion;
    
    @JoinColumn(name = "zonaextraccion")
    @ManyToOne(cascade = CascadeType.PERSIST)
    public Zona zonaExtraccion;
    
    @Column(name = "gasexceso")
    private double GasExceso;
    
    @Column(name = "cargouso")
    private double CargoUso;
    
    @Column(name = "cargogasexceso")
    private double CargoGasExceso;
    
    @Column(name = "facturatotal")
    private double FacturaTotal;
    
    @JoinColumn(name="idcantidad")
    @ManyToOne(cascade = CascadeType.PERSIST)
    public Cantidad cantidad;

    public int getIdtransaccion() {
        return Idtransaccion;
    }

    public void setIdtransaccion(int Idtransaccion) {
        this.Idtransaccion = Idtransaccion;
    }

    public Date getFechaRegistro() {
        return FechaRegistro;
    }

    public void setFechaRegistro(Date FechaRegistro) {
        this.FechaRegistro = FechaRegistro;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public NodoRecepcion getNodoRecepcion() {
        return nodoRecepcion;
    }

    public void setNodoRecepcion(NodoRecepcion nodoRecepcion) {
        this.nodoRecepcion = nodoRecepcion;
    }

    public NodoEntrega getNodoEntrega() {
        return nodoEntrega;
    }

    public void setNodoEntrega(NodoEntrega nodoEntrega) {
        this.nodoEntrega = nodoEntrega;
    }

    public Zona getZonaInyeccion() {
        return zonaInyeccion;
    }

    public void setZonaInyeccion(Zona zonaInyeccion) {
        this.zonaInyeccion = zonaInyeccion;
    }

    public Zona getZonaExtraccion() {
        return zonaExtraccion;
    }

    public void setZonaExtraccion(Zona zonaExtraccion) {
        this.zonaExtraccion = zonaExtraccion;
    }

    public double getGasExceso() {
        return GasExceso;
    }

    public void setGasExceso(double GasExceso) {
        this.GasExceso = GasExceso;
    }

    public double getCargoUso() {
        return CargoUso;
    }

    public void setCargoUso(double CargoUso) {
        this.CargoUso = CargoUso;
    }

    public double getCargoGasExceso() {
        return CargoGasExceso;
    }

    public void setCargoGasExceso(double CargoGasExceso) {
        this.CargoGasExceso = CargoGasExceso;
    }

    public double getFacturaTotal() {
        return FacturaTotal;
    }

    public void setFacturaTotal(double FacturaTotal) {
        this.FacturaTotal = FacturaTotal;
    }

    public Cantidad getCantidad() {
        return cantidad;
    }

    public void setCantidad(Cantidad cantidad) {
        this.cantidad = cantidad;
    }
}
