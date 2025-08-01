package com.CRamirezEvaluacionContratosPetroleo.Configuration;

import com.CRamirezEvaluacionContratosPetroleo.Service.JwtService; // Asegúrate de que esta importación sea correcta
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull; // Importar NonNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService; // Asegúrate de que esta importación sea correcta
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService; // Inyecta tu UserDetailsService

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userName; // Cambiado de userEmail a userName para consistencia con tu modelo

        System.out.println("DEBUG (JwtFilter): Solicitud entrante para URL: " + request.getRequestURI());
        System.out.println("DEBUG (JwtFilter): Header Authorization: " + authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("DEBUG (JwtFilter): No se encontró token JWT o formato incorrecto. Continuando la cadena de filtros.");
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7); // Extrae el token JWT (después de "Bearer ")
        System.out.println("DEBUG (JwtFilter): Token JWT extraído: " + jwt);

        try {
            userName = jwtService.extractUsername(jwt); // Extrae el nombre de usuario del token
            System.out.println("DEBUG (JwtFilter): Username extraído del token: " + userName);

            // Si el nombre de usuario se extrajo y no hay autenticación actual en el contexto
            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
                System.out.println("DEBUG (JwtFilter): UserDetails cargado para: " + userName + ", Roles: " + userDetails.getAuthorities());

                // Valida el token
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    System.out.println("DEBUG (JwtFilter): Token JWT es VÁLIDO.");
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null, // No necesitamos las credenciales aquí, ya están autenticadas
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    // Establece el objeto de autenticación en el SecurityContextHolder
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println("DEBUG (JwtFilter): Autenticación establecida en SecurityContextHolder.");
                } else {
                    System.out.println("DEBUG (JwtFilter): Token JWT es INVÁLIDO.");
                }
            } else {
                System.out.println("DEBUG (JwtFilter): Username es nulo o ya hay autenticación en el contexto.");
            }
        } catch (Exception e) {
            System.err.println("ERROR (JwtFilter): Error al procesar el token JWT: " + e.getMessage());
        }

        filterChain.doFilter(request, response); // Continúa la cadena de filtros
    }
}
