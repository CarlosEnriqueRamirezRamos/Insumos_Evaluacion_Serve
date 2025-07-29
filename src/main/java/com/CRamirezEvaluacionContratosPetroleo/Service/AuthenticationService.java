package com.CRamirezEvaluacionContratosPetroleo.Service;

import com.CRamirezEvaluacionContratosPetroleo.JPA.Usuario; // Importa tu entidad Usuario
import com.CRamirezEvaluacionContratosPetroleo.JPA.Rol; // Importa tu entidad Rol
import com.CRamirezEvaluacionContratosPetroleo.Repository.UsuarioRepository; // Importa tu repositorio de Usuario
import com.CRamirezEvaluacionContratosPetroleo.Repository.RolRepository; // Importa tu repositorio de Rol

import lombok.RequiredArgsConstructor; // Para la inyección de dependencias por constructor
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // Genera un constructor con los campos 'final' para inyección
public class AuthenticationService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository; // Necesario para buscar el Rol por ID
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        // 1. Buscar el Rol por su ID
        // Asegúrate de que el tipo de ID en RolRepository.findById() coincida con el tipo de IdRol en tu entidad Rol
        // Si el IdRol en la entidad Rol es 'int', entonces JpaRepository<Rol, Integer>
        // y el método findById() espera un Integer.
        Rol rol = rolRepository.findById(request.getIdRol()) // <-- ¡Aquí se usa el ID directamente!
                                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + request.getIdRol()));

        // 2. Crear una nueva instancia de Usuario
        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setUserName(request.getUserName());
        usuario.setPassword(passwordEncoder.encode(request.getPassword())); // ¡Encriptar la contraseña!
        usuario.setRol(rol); // Asignar el objeto Rol
        usuario.setStatus(1); // Por defecto, el usuario está activo (puedes cambiar esta lógica)

        // 3. Guardar el usuario en la base de datos
        usuarioRepository.save(usuario);

        // 4. Generar el token JWT para el usuario registrado
        String jwtToken = jwtService.generateToken(usuario);

        // 5. Devolver la respuesta con el token
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // 1. Intentar autenticar al usuario con el AuthenticationManager de Spring Security
        // Si las credenciales son incorrectas, lanzará una excepción (ej. BadCredentialsException)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserName(), // Nombre de usuario
                        request.getPassword() // Contraseña sin encriptar
                )
        );

        // 2. Si la autenticación es exitosa, buscar el usuario en la base de datos
        // (ya sabemos que existe por el paso anterior)
        var usuario = usuarioRepository.findByUserName(request.getUserName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado después de autenticación exitosa.")); // Mensaje más descriptivo

        // 3. Generar el token JWT para el usuario autenticado
        var jwtToken = jwtService.generateToken(usuario);

        // 4. Devolver la respuesta con el token
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}