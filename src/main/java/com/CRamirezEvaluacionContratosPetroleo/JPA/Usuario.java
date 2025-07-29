package com.CRamirezEvaluacionContratosPetroleo.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.FetchType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

@Entity
public class Usuario implements UserDetails {

    @Id
    @Column(name = "idusuario")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int IdUsuario;

    @Column(name = "nombre")
    private String Nombre;

    @JoinColumn(name = "idrol")
    @OneToOne(fetch = FetchType.EAGER)
    public Rol Rol;

    @Column(name = "status")
    private int Status;

    @Column(name = "password") // Esta es la propiedad de la clase
    private String Password;

    @Column(name = "username", unique = true) // Esta es la propiedad de la clase
    private String userName;

    // Constructor vacío (necesario para JPA)
    public Usuario() {
    }

    // --- Getters y Setters existentes ---
    public int getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(int IdUsuario) {
        this.IdUsuario = IdUsuario;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public Rol getRol() {
        return Rol;
    }

    public void setRol(Rol Rol) {
        this.Rol = Rol;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    @Override
    public String getPassword() {
        return Password; // Retorna el valor de tu campo 'Password'
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    @Override
    public String getUsername() {
        return userName; // Retorna el valor de tu campo 'UserName'
    }

    public void setUserName(String UserName) {
        this.userName = UserName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (this.Rol != null && this.Rol.getRol() != null) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + this.Rol.getRol().toUpperCase()));
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.Status == 1;
    }
}
