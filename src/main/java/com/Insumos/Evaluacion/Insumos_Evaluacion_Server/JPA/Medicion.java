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
    @JoinColumn(name = "ID_TRANSACCION", nullable = false, unique = true)
    private Transaccion transaccion;
    
    @Column(name = "CANT_NOM_RECEPCION")
    private Double cantNomRecepcion;
    
    @Column(name = "CANT_ASIG_RECEPCION")
    private Double cantAsigRecepcion;
    
    @Column(name = "CANT_NOM_ENTREGA")
    private Double cantNomEntrega;
    
    @Column(name = "CANT_ASIG_ENTREGA")
    private Double cantAsigEntrega;
    
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

    public Double getCantNomRecepcion() {
        return cantNomRecepcion;
    }

    public void setCantNomRecepcion(Double cantNomRecepcion) {
        this.cantNomRecepcion = cantNomRecepcion;
    }

    public Double getCantAsigRecepcion() {
        return cantAsigRecepcion;
    }

    public void setCantAsigRecepcion(Double cantAsigRecepcion) {
        this.cantAsigRecepcion = cantAsigRecepcion;
    }

    public Double getCantNomEntrega() {
        return cantNomEntrega;
    }

    public void setCantNomEntrega(Double cantNomEntrega) {
        this.cantNomEntrega = cantNomEntrega;
    }

    public Double getCantAsigEntrega() {
        return cantAsigEntrega;
    }

    public void setCantAsigEntrega(Double cantAsigEntrega) {
        this.cantAsigEntrega = cantAsigEntrega;
    }

    public Double getGasEnExceso() {
        return gasEnExceso;
    }

    public void setGasEnExceso(Double gasEnExceso) {
        this.gasEnExceso = gasEnExceso;
    }
    
    
}