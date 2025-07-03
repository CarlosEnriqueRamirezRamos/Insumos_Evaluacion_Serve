package com.Insumos.Evaluacion.Insumos_Evaluacion_Server.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class TransaccionZona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TRANSACCION_ZONA")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "ID_TRANSACCION", nullable = false)
    private Transaccion transaccion;
    
    @ManyToOne
    @JoinColumn(name = "ID_ZONA", nullable = false)
    private ZonaTarifa zona;
    
    @ManyToOne
    @JoinColumn(name = "ID_TIPO", nullable = false)
    private ZonaTipo tipo;

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

    public ZonaTarifa getZona() {
        return zona;
    }

    public void setZona(ZonaTarifa zona) {
        this.zona = zona;
    }

    public ZonaTipo getTipo() {
        return tipo;
    }

    public void setTipo(ZonaTipo tipo) {
        this.tipo = tipo;
    }
    
    
}