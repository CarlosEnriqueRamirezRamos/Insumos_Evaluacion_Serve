package com.CRamirezEvaluacionContratosPetroleo.JPA;

import java.util.ArrayList;
import java.util.List;

public class ResultadoLectura {

    private List<Zona> zonas;
    private List<Usuario> usuarios;
    private List<NodoRecepcion> nrecepciones;
    private List<NodoEntrega> nentregas;
    private List<Cantidad> cantidades;
    private List<Contrato> contratos;
    private List<Transaccion> transacciones;
    private List<Tarifa> tarifas;

    public ResultadoLectura() {
        this.zonas = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.nrecepciones = new ArrayList<>();
        this.nentregas = new ArrayList<>();
        this.cantidades = new ArrayList<>();
        this.contratos = new ArrayList<>();
        this.transacciones = new ArrayList<>();
        this.tarifas = new ArrayList<>();
    }

    public List<Zona> getZonas() {
        return zonas;
    }

    public void setZonas(List<Zona> zonas) {
        this.zonas = zonas;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<NodoRecepcion> getNrecepciones() {
        return nrecepciones;
    }

    public void setNrecepciones(List<NodoRecepcion> nrecepciones) {
        this.nrecepciones = nrecepciones;
    }

    public List<NodoEntrega> getNentregas() {
        return nentregas;
    }

    public void setNentregas(List<NodoEntrega> nentregas) {
        this.nentregas = nentregas;
    }

    public List<Cantidad> getcantidades() {
        return cantidades;
    }

    public void setcantidades(List<Cantidad> ncantidades) {
        this.cantidades = ncantidades;
    }

    public List<Contrato> getcontratos() {
        return contratos;
    }

    public void setcontratos(List<Contrato> ncontratos) {
        this.contratos = ncontratos;
    }

    public List<Cantidad> getCantidades() {
        return cantidades;
    }

    public void setCantidades(List<Cantidad> cantidades) {
        this.cantidades = cantidades;
    }

    public List<Contrato> getContratos() {
        return contratos;
    }

    public void setContratos(List<Contrato> contratos) {
        this.contratos = contratos;
    }

    public List<Transaccion> getTransacciones() {
        return transacciones;
    }

    public void setTransacciones(List<Transaccion> transacciones) {
        this.transacciones = transacciones;
    }

    public List<Tarifa> getTarifas() {
        return tarifas;
    }

    public void setTarifas(List<Tarifa> tarifas) {
        this.tarifas = tarifas;
    }
    
    
}
