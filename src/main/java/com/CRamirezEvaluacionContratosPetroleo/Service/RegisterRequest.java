package com.CRamirezEvaluacionContratosPetroleo.Service; // O com.CRamirezEvaluacionContratosPetroleo.DTO.request

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals, hashCode
@Builder // Permite construir objetos de forma fluida
@NoArgsConstructor // Constructor sin argumentos
@AllArgsConstructor // Constructor con todos los argumentos
public class RegisterRequest {
    private String nombre; // Nombre completo del usuario
    private String userName; // El nombre de usuario único para login
    private String password; // La contraseña sin encriptar
    private int idRol; // El ID del rol que se le asignará al usuario
}