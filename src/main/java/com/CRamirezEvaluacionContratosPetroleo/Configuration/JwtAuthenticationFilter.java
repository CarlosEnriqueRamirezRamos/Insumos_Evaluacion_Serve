package com.CRamirezEvaluacionContratosPetroleo.Configuration;

import com.CRamirezEvaluacionContratosPetroleo.Service.JwtService;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService; // Lo usaremos para cargar el usuario desde la DB

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain // Cadena de filtros de Spring
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization"); // Busca el header Authorization
        final String jwt;
        final String userEmail;

        // Si no hay header o no empieza con "Bearer ", pasamos al siguiente filtro
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7); // Extrae el token (quita "Bearer ")
        userEmail = jwtService.extractUsername(jwt); // Obtiene el username del token

        // Si hay un username y no hay una autenticación ya en el contexto de seguridad
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail); // Carga los detalles del usuario de la DB
            if (jwtService.isTokenValid(jwt, userDetails)) { // Valida el token
                // Si es válido, creamos un objeto de autenticación
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, // La contraseña ya no es necesaria, ya fue verificada
                        userDetails.getAuthorities() // Los roles/permisos del usuario
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                // Establece la autenticación en el SecurityContext, para que Spring Security sepa quién es el usuario
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response); // Continúa la cadena de filtros
    }
}
