package com.CRamirezEvaluacionContratosPetroleo.Service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
    private String token; // El token JWT generado
    // Puedes añadir más datos si lo necesitas, como el nombre del usuario, roles, etc.
}