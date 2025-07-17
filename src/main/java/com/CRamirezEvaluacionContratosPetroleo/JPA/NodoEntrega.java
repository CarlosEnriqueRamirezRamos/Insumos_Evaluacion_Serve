
package com.CRamirezEvaluacionContratosPetroleo.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "nodoentrega")
public class NodoEntrega {
    
    @Id
    @Column(name = "idnodoentrega")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int IdNodoEntrega;
    
    @Column(name = "clave")
    private String Clave;
    
    @Column(name = "descripcion")
    private String Descripcion;

    public int getIdNodoEntrega() {
        return IdNodoEntrega;
    }

    public void setIdNodoEntrega(int IdNodoEntrega) {
        this.IdNodoEntrega = IdNodoEntrega;
    }

    public String getClave() {
        return Clave;
    }

    public void setClave(String Clave) {
        this.Clave = Clave;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }
    
}
