package com.Insumos.Evaluacion.Insumos_Evaluacion_Server.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Tarifa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TARIFA")
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "ID_TRANSACCION", nullable = false, unique = true)
    private Transaccion transaccion;
    
    @Column(name = "TARIFA_EXCESO_FIRME")
    private Double tarifaExcesoFirme;
    
    @Column(name = "TARIFA_USO_INTERRUP")
    private Double tarifaUsoInterrup;

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

    public Double getTarifaUsoInterrup() {
        return tarifaUsoInterrup;
    }

    public void setTarifaUsoInterrup(Double tarifaUsoInterrup) {
        this.tarifaUsoInterrup = tarifaUsoInterrup;
    }
    
}