package com.CRamirezEvaluacionContratosPetroleo.JPA;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Usuario") // Asegúrate de que el nombre de la tabla sea correcto en tu DB
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdUsuario")
    private Integer idUsuario;

    @Column(name = "Nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "UserName", nullable = false, unique = true, length = 50)
    private String userName;

    @Column(name = "Password", nullable = false, length = 255)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER) // Carga el rol inmediatamente con el usuario
    @JoinColumn(name = "IdRol", nullable = false) // Columna en la tabla Usuarios que es FK a Rol
    private Rol rol; // Asumiendo que esta es tu entidad Rol

    @Column(name = "Status", nullable = false)
    private Integer status; // 1 para activo, 0 para inactivo

    // --- Métodos de la interfaz UserDetails ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // ¡CORRECCIÓN AQUÍ! Se cambió 'getNombre()' a 'getRol()'
        // Asumiendo que tu entidad Rol tiene un campo 'Rol' y su getter 'getRol()'.
        return List.of(new SimpleGrantedAuthority("ROLE_" + rol.getRol().toUpperCase()));
    }

    @Override
    public String getUsername() {
        return userName; // Retorna el nombre de usuario para Spring Security
    }

    @Override
    public String getPassword() {
        return password; // Retorna la contraseña (ya encriptada)
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Implementa tu lógica de expiración de cuenta si es necesario
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Implementa tu lógica de bloqueo de cuenta si es necesario
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Implementa tu lógica de expiración de credenciales si es necesario
    }

    @Override
    public boolean isEnabled() {
        // La cuenta está habilitada si el status es 1
        return this.status == 1;
    }
}
