package com.CRamirezEvaluacionContratosPetroleo.RestController;

import com.CRamirezEvaluacionContratosPetroleo.Service.AuthenticationRequest; // Importa tu DTO de solicitud de autenticación
import com.CRamirezEvaluacionContratosPetroleo.Service.AuthenticationResponse; // Importa tu DTO de respuesta de autenticación
import com.CRamirezEvaluacionContratosPetroleo.Service.AuthenticationService; // Importa tu servicio de autenticación
import com.CRamirezEvaluacionContratosPetroleo.Service.RegisterRequest; // Importa tu DTO de solicitud de registro

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService; // Inyecta tu servicio de autenticación

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        AuthenticationResponse response = authenticationService.register(request);
        return ResponseEntity.ok(response); // Devuelve una respuesta HTTP 200 OK con el token
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        AuthenticationResponse response = authenticationService.authenticate(request);
        return ResponseEntity.ok(response); // Devuelve una respuesta HTTP 200 OK con el token
    }
}
