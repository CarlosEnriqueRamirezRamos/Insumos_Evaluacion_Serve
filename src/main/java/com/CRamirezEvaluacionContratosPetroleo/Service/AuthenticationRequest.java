package com.CRamirezEvaluacionContratosPetroleo.Service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {
    private String userName; // El nombre de usuario para login
    private String password; // La contraseña sin encriptar
}