package com.CRamirezEvaluacionContratosPetroleo.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "nodorecepcion")
public class NodoRecepcion {

    @Id
    @Column(name = "idnodorecepcion")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int IdNodoRecepcion;
    
    @Column(name = "clave")
    String Clave;
    
    @Column(name = "descripcion")
    String Descripcion;

    public int getIdNodoRecepcion() {
        return IdNodoRecepcion;
    }

    public void setIdNodoRecepcion(int IdNodoRecepcion) {
        this.IdNodoRecepcion = IdNodoRecepcion;
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
