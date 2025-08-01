package com.CRamirezEvaluacionContratosPetroleo.Service;

import com.CRamirezEvaluacionContratosPetroleo.Repository.UsuarioRepository;
import com.CRamirezEvaluacionContratosPetroleo.JPA.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Obtenemos la entidad Usuario del servidor
        Usuario usuario = usuarioRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con username: " + username));

        // ¡SIMPLIFICACIÓN CLAVE!
        // Devuelve directamente tu entidad Usuario, ya que implementa UserDetails.
        // Spring Security llamará a getUsername(), getPassword(), getAuthorities(), etc., de esta instancia.
        return usuario; // Ahora es explícito que devolvemos la instancia de Usuario
    }
}
