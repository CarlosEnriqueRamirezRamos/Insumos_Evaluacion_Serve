package com.Insumos.Evaluacion.Insumos_Evaluacion_Server.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CARGO")
    private Long id;

    @OneToOne
    @JoinColumn(name = "ID_TRANSACCION")
    private Transaccion transaccion;

    @Column(name = "CARGO_USO")
    private Double cargoUso;

    @Column(name = "CARGO_EXCESO")
    private Double cargoExceso;

    @Column(name = "TOTAL")
    private Double totalFacturar;

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

    public Double getCargoUso() {
        return cargoUso;
    }

    public void setCargoUso(Double cargoUso) {
        this.cargoUso = cargoUso;
    }

    public Double getCargoExceso() {
        return cargoExceso;
    }

    public void setCargoExceso(Double cargoExceso) {
        this.cargoExceso = cargoExceso;
    }

    public Double getTotalFacturar() {
        return totalFacturar;
    }

    public void setTotalFacturar(Double totalFacturar) {
        this.totalFacturar = totalFacturar;
    }
    
}
