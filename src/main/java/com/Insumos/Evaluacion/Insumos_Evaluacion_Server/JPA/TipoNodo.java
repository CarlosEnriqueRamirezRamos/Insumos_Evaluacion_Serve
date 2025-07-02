package com.Insumos.Evaluacion.Insumos_Evaluacion_Server.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class TipoNodo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TIPO_NODO")
    private Long id;

    @Column(name = "NOMBRE_NODO", nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "tipoNodo")
    private List<NodoComercial> nodos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<NodoComercial> getNodos() {
        return nodos;
    }

    public void setNodos(List<NodoComercial> nodos) {
        this.nodos = nodos;
    }
    
}
