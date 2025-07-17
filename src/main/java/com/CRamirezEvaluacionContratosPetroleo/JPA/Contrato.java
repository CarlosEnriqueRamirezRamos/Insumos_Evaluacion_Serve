package com.CRamirezEvaluacionContratosPetroleo.JPA;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class Contrato {

    @Id
    @Column(name = "idcontrato")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int IdContrato;

    @Column(name = "clavecontrato")
    private String ClaveContrato;

    @JoinColumn(name = "idusuario")
    @ManyToOne
    public Usuario usuario;

    public int getIdContrato() {
        return IdContrato;
    }

    public void setIdContrato(int IdContrato) {
        this.IdContrato = IdContrato;
    }

    public String getClaveContrato() {
        return ClaveContrato;
    }

    public void setClaveContrato(String ClaveContrato) {
        this.ClaveContrato = ClaveContrato;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
