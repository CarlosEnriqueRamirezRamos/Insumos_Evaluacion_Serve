package com.CRamirezEvaluacionContratosPetroleo.Service; // O en un subpaquete de servicio para seguridad

import com.CRamirezEvaluacionContratosPetroleo.Repository.UsuarioRepository; // Importa tu repositorio de Usuario
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // Marca esta clase como un componente de servicio de Spring
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository; // Inyectamos tu repositorio de Usuario

    @Autowired // Inyección de dependencias a través del constructor
    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con username: " + username));
    }
}