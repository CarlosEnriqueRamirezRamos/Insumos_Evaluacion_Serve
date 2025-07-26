package com.CRamirezEvaluacionContratosPetroleo.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Tarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtarifa")
    private int IdTarifa;

    @Column(name = "excesofirme")
    private double ExcesoFirme;

    @Column(name = "usointerrumpible")
    private double UsoInterrumpible;

    @JoinColumn(name = "idtransaccion")
    @ManyToOne
    public Transaccion transaccion;

    public int getIdTarifa() {
        return IdTarifa;
    }

    public void setIdTarifa(int IdTarifa) {
        this.IdTarifa = IdTarifa;
    }

    public double getExcesoFirme() {
        return ExcesoFirme;
    }

    public void setExcesoFirme(double ExcesoFirme) {
        this.ExcesoFirme = ExcesoFirme;
    }

    public double getUsoInterrumpible() {
        return UsoInterrumpible;
    }

    public void setUsoInterrumpible(double UsoInterrumpible) {
        this.UsoInterrumpible = UsoInterrumpible;
    }

    public Transaccion getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(Transaccion transaccion) {
        this.transaccion = transaccion;
    }

}
