package com.Insumos.Evaluacion.Insumos_Evaluacion_Server.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Medicion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MEDICION")
    private Long id;

    @OneToOne
    @JoinColumn(name = "ID_TRANSACCION")
    private Transaccion transaccion;

    @Column(name = "CANTIDAD_NOM_RECEP")
    private Double cantidadNomRecepcion;

    @Column(name = "CANTIDAD_ASIG_RECEP")
    private Double cantidadAsigRecepcion;

    @Column(name = "CANTIDAD_NOM_ENTREGA")
    private Double cantidadNomEntrega;

    @Column(name = "CANTIDAD_ASIG_ENTREGA")
    private Double cantidadAsigEntrega;

    @Column(name = "GAS_EN_EXCESO")
    private Double gasEnExceso;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Transaccion getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(Transaccion transaccion) {
        this.transaccion = transaccion;
    }

    public Double getCantidadNomRecepcion() {
        return cantidadNomRecepcion;
    }

    public void setCantidadNomRecepcion(Double cantidadNomRecepcion) {
        this.cantidadNomRecepcion = cantidadNomRecepcion;
    }

    public Double getCantidadAsigRecepcion() {
        return cantidadAsigRecepcion;
    }

    public void setCantidadAsigRecepcion(Double cantidadAsigRecepcion) {
        this.cantidadAsigRecepcion = cantidadAsigRecepcion;
    }

    public Double getCantidadNomEntrega() {
        return cantidadNomEntrega;
    }

    public void setCantidadNomEntrega(Double cantidadNomEntrega) {
        this.cantidadNomEntrega = cantidadNomEntrega;
    }

    public Double getCantidadAsigEntrega() {
        return cantidadAsigEntrega;
    }

    public void setCantidadAsigEntrega(Double cantidadAsigEntrega) {
        this.cantidadAsigEntrega = cantidadAsigEntrega;
    }

    public Double getGasEnExceso() {
        return gasEnExceso;
    }

    public void setGasEnExceso(Double gasEnExceso) {
        this.gasEnExceso = gasEnExceso;
    }
    
}
