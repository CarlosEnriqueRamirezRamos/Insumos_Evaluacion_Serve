package com.Insumos.Evaluacion.Insumos_Evaluacion_Server.JPA;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;

@Entity
public class Transaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TRANSACCION")
    private Long id;

    @Column(name = "FECHA")
    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "ID_CONTRATO")
    private Contrato contrato;

    @ManyToOne
    @JoinColumn(name = "ID_DETALLE_RECEPCION")
    private DetalleTransaccion detalleRecepcion;

    @ManyToOne
    @JoinColumn(name = "ID_DETALLE_ENTREGA")
    private DetalleTransaccion detalleEntrega;

    @OneToOne(mappedBy = "transaccion", cascade = CascadeType.ALL)
    private Medicion medicion;

    @OneToOne(mappedBy = "transaccion", cascade = CascadeType.ALL)
    private Tarifa tarifa;

    @OneToOne(mappedBy = "transaccion", cascade = CascadeType.ALL)
    private Cargo cargo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public DetalleTransaccion getDetalleRecepcion() {
        return detalleRecepcion;
    }

    public void setDetalleRecepcion(DetalleTransaccion detalleRecepcion) {
        this.detalleRecepcion = detalleRecepcion;
    }

    public DetalleTransaccion getDetalleEntrega() {
        return detalleEntrega;
    }

    public void setDetalleEntrega(DetalleTransaccion detalleEntrega) {
        this.detalleEntrega = detalleEntrega;
    }

    public Medicion getMedicion() {
        return medicion;
    }

    public void setMedicion(Medicion medicion) {
        this.medicion = medicion;
    }

    public Tarifa getTarifa() {
        return tarifa;
    }

    public void setTarifa(Tarifa tarifa) {
        this.tarifa = tarifa;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
    
    
}
