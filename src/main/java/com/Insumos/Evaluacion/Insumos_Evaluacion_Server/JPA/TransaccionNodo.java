package com.Insumos.Evaluacion.Insumos_Evaluacion_Server.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class TransaccionNodo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TRANSACCION_NODO")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "ID_TRANSACCION", nullable = false)
    private Transaccion transaccion;
    
    @ManyToOne
    @JoinColumn(name = "ID_NODO", nullable = false)
    private NodoComercial nodo;
    
    @ManyToOne
    @JoinColumn(name = "ID_TIPO_NODO", nullable = false)
    private TipoNodo tipoNodo;

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

    public NodoComercial getNodo() {
        return nodo;
    }

    public void setNodo(NodoComercial nodo) {
        this.nodo = nodo;
    }

    public TipoNodo getTipoNodo() {
        return tipoNodo;
    }

    public void setTipoNodo(TipoNodo tipoNodo) {
        this.tipoNodo = tipoNodo;
    }
    
    
}