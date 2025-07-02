package com.Insumos.Evaluacion.Insumos_Evaluacion_Server.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Tarifa {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TARIFA")
    private Long id;

    @OneToOne
    @JoinColumn(name = "ID_TRANSACCION")
    private Transaccion transaccion;

    @Column(name = "TARIFA_EXCESO_FIRME")
    private Double tarifaExcesoFirme;

    @Column(name = "TARIFA_USO_INTERRUP")
    private Double tarifaUsoInterrumpible;

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

    public Double getTarifaExcesoFirme() {
        return tarifaExcesoFirme;
    }

    public void setTarifaExcesoFirme(Double tarifaExcesoFirme) {
        this.tarifaExcesoFirme = tarifaExcesoFirme;
    }

    public Double getTarifaUsoInterrumpible() {
        return tarifaUsoInterrumpible;
    }

    public void setTarifaUsoInterrumpible(Double tarifaUsoInterrumpible) {
        this.tarifaUsoInterrumpible = tarifaUsoInterrumpible;
    }
    
    
}
