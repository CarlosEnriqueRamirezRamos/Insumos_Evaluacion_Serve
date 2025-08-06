package com.CRamirezEvaluacionContratosPetroleo.RestController;

import com.CRamirezEvaluacionContratosPetroleo.DAO.ContratosDAOImplemetation;
import com.CRamirezEvaluacionContratosPetroleo.DAO.TransaccionDAOImplemetation;
import com.CRamirezEvaluacionContratosPetroleo.DAO.TarifaDAOImplemetation;
import com.CRamirezEvaluacionContratosPetroleo.DAO.UsuarioDAOImplemetation;
import com.CRamirezEvaluacionContratosPetroleo.JPA.Cantidad;
import com.CRamirezEvaluacionContratosPetroleo.JPA.Contrato;
import com.CRamirezEvaluacionContratosPetroleo.JPA.NodoEntrega;
import com.CRamirezEvaluacionContratosPetroleo.JPA.NodoRecepcion;
import com.CRamirezEvaluacionContratosPetroleo.JPA.Result;
import com.CRamirezEvaluacionContratosPetroleo.JPA.ResultadoLectura;
import com.CRamirezEvaluacionContratosPetroleo.JPA.Tarifa;
import com.CRamirezEvaluacionContratosPetroleo.JPA.Transaccion;
import com.CRamirezEvaluacionContratosPetroleo.JPA.Usuario;
import com.CRamirezEvaluacionContratosPetroleo.JPA.Zona;
import com.CRamirezEvaluacionContratosPetroleo.Repository.CantidadRepository;
import com.CRamirezEvaluacionContratosPetroleo.Repository.ContratoRepository;
import com.CRamirezEvaluacionContratosPetroleo.Repository.NodoEntregaRepository;
import com.CRamirezEvaluacionContratosPetroleo.Repository.NodoRecepcionRepository;
import com.CRamirezEvaluacionContratosPetroleo.Repository.TarifaRepository;
import com.CRamirezEvaluacionContratosPetroleo.Repository.TransaccionRepository;
import com.CRamirezEvaluacionContratosPetroleo.Repository.UsuarioRepository;
import com.CRamirezEvaluacionContratosPetroleo.Repository.ZonaRepository;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/Api") // Mapeo base para todos los endpoints de este controlador
@RequiredArgsConstructor
public class PetroleoRestController {

    // Se mantiene @Autowired para los repositorios y DAOs que no son 'final'
    @Autowired
    private ZonaRepository zonaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private NodoRecepcionRepository nodoRecepcionRepository;
    @Autowired
    private NodoEntregaRepository nodoEntregaRepository;
    @Autowired
    private CantidadRepository cantidadRepository;
    @Autowired
    private ContratoRepository contratoRepository;
    @Autowired
    private TransaccionRepository transaccionRepository;
    @Autowired
    private TarifaRepository tarifaRepository;

    private final TransaccionDAOImplemetation transaccionDAOImplemetation;
    private final TarifaDAOImplemetation tarifaDAOImplemetation;
    private final ContratosDAOImplemetation contratosDAOImplemetation;
    private final UsuarioDAOImplemetation usuarioDAOImplemetation;

    // Método LecturaArchivo integrado de nuevo en esta clase
    private ResultadoLectura LecturaArchivo(File archivo) throws IOException {
        ResultadoLectura resultado = new ResultadoLectura();

        Set<String> ZonaUnica = new HashSet<>();
        List<Zona> listaZonas = new ArrayList<>();
        Set<String> UsuarioUnico = new HashSet<>();
        List<Usuario> listaUsuarios = new ArrayList<>();
        Set<String> NRecepcionUnico = new HashSet<>();
        List<NodoRecepcion> listaNodoRecepcions = new ArrayList<>();
        Set<String> NEntrgaUnico = new HashSet<>();
        List<NodoEntrega> listaNodoEntregas = new ArrayList<>();
        List<Cantidad> listaCantidades = new ArrayList<>();
        Set<String> ContratoUnico = new HashSet<>();
        List<Contrato> listaContratos = new ArrayList<>();
        List<Transaccion> listaTransacciones = new ArrayList<>();
        List<Tarifa> listaTarifas = new ArrayList<>();

        try (XSSFWorkbook workbook = new XSSFWorkbook(archivo)) {
            for (Sheet listaZona : workbook) {
                for (Row row : listaZona) {
                    if (row.getRowNum() == 0) {
                        continue;
                    }
                    //Zonas
                    Zona zona = new Zona();
                    String ZonaInyeccion = row.getCell(7).getStringCellValue();
                    String ZonaExtraccion = row.getCell(8).getStringCellValue();

                    if (!ZonaUnica.contains(ZonaInyeccion)) {
                        ZonaUnica.add(ZonaInyeccion);
                        zona.setDescripcion(ZonaInyeccion);
                        listaZonas.add(zona);
                    } else if (!ZonaUnica.contains(ZonaExtraccion)) {
                        ZonaUnica.add(ZonaExtraccion);
                        zona.setDescripcion(ZonaExtraccion);
                        listaZonas.add(zona);
                    }

                    //Usuarios
                    Usuario usuario = new Usuario();
                    String NombreUsuario = row.getCell(2).getStringCellValue();

                    if (!UsuarioUnico.contains(NombreUsuario)) {
                        UsuarioUnico.add(NombreUsuario);
                        usuario.setNombre(NombreUsuario);
                        listaUsuarios.add(usuario);
                    }

                    //Nodo de recepcion
                    NodoRecepcion nodoRecepcion = new NodoRecepcion();
                    String ClaveRecepcion = row.getCell(3).getStringCellValue();

                    if (!NRecepcionUnico.contains(ClaveRecepcion)) {
                        NRecepcionUnico.add(ClaveRecepcion);
                        nodoRecepcion.setClave(ClaveRecepcion);
                        nodoRecepcion.setDescripcion(row.getCell(4).getStringCellValue());
                        listaNodoRecepcions.add(nodoRecepcion);

                    }

                    //Nodo de Entrega
                    NodoEntrega nodoEntrega = new NodoEntrega();
                    String ClaveEntrega = row.getCell(5).getStringCellValue();

                    if (!NEntrgaUnico.contains(ClaveEntrega)) {
                        NEntrgaUnico.add(ClaveEntrega);
                        nodoEntrega.setClave(ClaveEntrega);
                        nodoEntrega.setDescripcion(row.getCell(6).getStringCellValue());
                        listaNodoEntregas.add(nodoEntrega);
                    }

                    //Cantidad
                    Cantidad cantidad = new Cantidad();
                    cantidad.setNominadaRecepcion(row.getCell(9).getNumericCellValue());
                    cantidad.setAsignadaRecepcion(row.getCell(10).getNumericCellValue());
                    cantidad.setNominadaEntrega(row.getCell(11).getNumericCellValue());
                    cantidad.setAsignadaEntrega(row.getCell(12).getNumericCellValue());
                    listaCantidades.add(cantidad);

                    //Contrato
                    Contrato contrato = new Contrato();
                    contrato.setUsuario(new Usuario()); // Usar setter
                    String ClaveContrato = row.getCell(1).getStringCellValue();
                    if (!ContratoUnico.contains(ClaveContrato)) {
                        ContratoUnico.add(ClaveContrato);
                        contrato.setClaveContrato(ClaveContrato);
                        contrato.getUsuario().setNombre(row.getCell(2).getStringCellValue()); // Usar getter y setter
                        listaContratos.add(contrato);
                    }

                    //Transaccion
                    Transaccion transaccion = new Transaccion();
                    transaccion.setContrato(new Contrato()); // Usar setter
                    transaccion.setNodoRecepcion(new NodoRecepcion()); // Usar setter
                    transaccion.setNodoEntrega(new NodoEntrega()); // Usar setter
                    transaccion.setZonaInyeccion(new Zona()); // Usar setter
                    transaccion.setZonaExtraccion(new Zona()); // Usar setter
                    transaccion.setFechaRegistro(row.getCell(0).getDateCellValue());
                    transaccion.getContrato().setClaveContrato(ClaveContrato); // Usar getter y setter
                    transaccion.getNodoRecepcion().setClave(ClaveRecepcion); // Usar getter y setter
                    transaccion.getNodoEntrega().setClave(ClaveEntrega); // Usar getter y setter
                    transaccion.getZonaInyeccion().setDescripcion(ZonaInyeccion); // Usar getter y setter
                    transaccion.getZonaExtraccion().setDescripcion(ZonaExtraccion); // Usar getter y setter
                    transaccion.setGasExceso(row.getCell(13).getNumericCellValue());
                    transaccion.setCargoUso(row.getCell(16).getNumericCellValue());
                    transaccion.setCargoGasExceso(row.getCell(17).getNumericCellValue());
                    transaccion.setFacturaTotal(row.getCell(18).getNumericCellValue());
                    transaccion.setCantidad(cantidad);
                    listaTransacciones.add(transaccion);

                    //Tarifa
                    Tarifa tarifa = new Tarifa();
                    tarifa.setTransaccion(new Transaccion()); // Usar setter

                    tarifa.setExcesoFirme(row.getCell(14).getNumericCellValue());
                    tarifa.setUsoInterrumpible(row.getCell(15).getNumericCellValue());
                    tarifa.setTransaccion(transaccion);
                    listaTarifas.add(tarifa);
                }
            }

        } catch (Exception ex) {
            System.err.println("ERROR (LecturaArchivo): Hubo un error al leer el archivo Excel:");
            ex.printStackTrace();
            throw new IOException("Error al procesar el archivo Excel: " + ex.getMessage(), ex);
        }
        resultado.setZonas(listaZonas);
        resultado.setUsuarios(listaUsuarios);
        resultado.setNrecepciones(listaNodoRecepcions);
        resultado.setNentregas(listaNodoEntregas);
        resultado.setcantidades(listaCantidades);
        resultado.setcontratos(listaContratos);
        resultado.setTransacciones(listaTransacciones);
        resultado.setTarifas(listaTarifas);
        return resultado;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
    @GetMapping("/User-info")
    public ResponseEntity<?> getUserInfo() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                
                Map<String, Object> userInfo = new HashMap<>();
                userInfo.put("username", userDetails.getUsername());
                userInfo.put("roles", userDetails.getAuthorities().stream()
                                                .map(GrantedAuthority::getAuthority)
                                                .collect(Collectors.toList()));
                
                if (userDetails instanceof com.CRamirezEvaluacionContratosPetroleo.JPA.Usuario) {
                    com.CRamirezEvaluacionContratosPetroleo.JPA.Usuario usuarioEntity = 
                        (com.CRamirezEvaluacionContratosPetroleo.JPA.Usuario) userDetails;
                    userInfo.put("nombre", usuarioEntity.getNombre());
                    userInfo.put("idUsuario", usuarioEntity.getIdUsuario());
                    userInfo.put("status", usuarioEntity.getStatus());
                }

                System.out.println("DEBUG (UserInfo): Acceso exitoso a /User-info para: " + userDetails.getUsername());
                return ResponseEntity.ok(userInfo);
            } else {
                System.err.println("ERROR (UserInfo): Usuario no autenticado o principal no es UserDetails.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado.");
            }
        } catch (Exception e) {
            System.err.println("ERROR (UserInfo): Excepción al procesar /api/User-info:");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor al obtener información del usuario.");
        }
    }

    // Este endpoint es para obtener TODAS las transacciones
    @GetMapping("/Transacciones") // Endpoint para obtener todas las transacciones
    @PreAuthorize("hasAnyAuthority('ROLE_ADMINISTRADOR', 'ROLE_USUARIO')")
    public ResponseEntity<Result<List<Transaccion>>> getAllTransacciones() {
        System.out.println("DEBUG (PetroleoRestController): Accediendo a GET /Api/Transacciones");
        try {
            Result<List<Transaccion>> result = transaccionDAOImplemetation.GetAll();
            System.out.println("DEBUG (PetroleoRestController): transaccionDAOImplemetation.GetAll() - Correct: " + result.correct);
            if (!result.correct) {
                System.err.println("DEBUG (PetroleoRestController): Error en DAO: " + result.errorMessage);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            System.err.println("DEBUG (PetroleoRestController): Excepción al obtener todas las transacciones: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.error("Error interno del servidor al obtener transacciones."));
        }
    }

    @GetMapping("/Tarifa/{idTransaccion}")
    public ResponseEntity<Result<List<Tarifa>>> getTarifa(@PathVariable int idTransaccion) {
        System.out.println("DEBUG (PetroleoRestController): Accediendo a GET /Api/Tarifa/" + idTransaccion);
        try {
            Result<List<Tarifa>> result = tarifaDAOImplemetation.GetAll(idTransaccion);
            System.out.println("DEBUG (PetroleoRestController): tarifaDAOImplemetation.GetAll() - Correct: " + result.correct);
            if (!result.correct) {
                System.err.println("DEBUG (PetroleoRestController): Error en DAO: " + result.errorMessage);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            System.err.println("DEBUG (PetroleoRestController): Excepción al obtener tarifa: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.error("Error interno del servidor al obtener tarifa."));
        }
    }

    @GetMapping("/Contratos/{idUsuario}")
    public ResponseEntity<Result<List<Contrato>>> getContratos(@PathVariable int idUsuario) {
        System.out.println("DEBUG (PetroleoRestController): Accediendo a GET /Api/Contratos/" + idUsuario);
        try {
            Result<List<Contrato>> result = contratosDAOImplemetation.Getbyid(idUsuario);
            System.out.println("DEBUG (PetroleoRestController): contratosDAOImplemetation.Getbyid() - Correct: " + result.correct);
            if (!result.correct) {
                System.err.println("DEBUG (PetroleoRestController): Error en DAO: " + result.errorMessage);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            System.err.println("DEBUG (PetroleoRestController): Excepción al obtener contratos: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.error("Error interno del servidor al obtener contratos."));
        }
    }

    @PostMapping("/CargaMasiva")
    public ResponseEntity<Result<Void>> CargaMasiva(@RequestParam("archivo") MultipartFile archivo) {
        System.out.println("DEBUG (PetroleoRestController): Accediendo a POST /Api/CargaMasiva");
        Result<Void> result = new Result<>();
        try {
            if (archivo != null && !archivo.isEmpty()) {
                String root = System.getProperty("user.dir");
                String path = "src/main/resources/static/Archivos";
                File uploadDir = new File(root + "/" + path);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy-MM-dd HH-mm-SS"));
                String originalFilename = archivo.getOriginalFilename();
                String safeFilename = originalFilename.replaceAll("[^a-zA-Z0-9.-]", "_");
                String absolutePath = root + "/" + path + "/" + fecha + "_" + safeFilename;
                
                File destFile = new File(absolutePath);
                archivo.transferTo(destFile);

                // Llamada directa al método LecturaArchivo dentro de esta clase
                ResultadoLectura resultadoLectura = this.LecturaArchivo(destFile);

                ResponseEntity<Result<Void>> procesarResponse = procesar(resultadoLectura);
                if (!procesarResponse.getStatusCode().is2xxSuccessful()) {
                    return procesarResponse;
                }
            } else {
                result.correct = false;
                result.errorMessage = "El archivo no fue proporcionado o está vacío.";
                return ResponseEntity.badRequest().body(result);
            }
            result.correct = true;
            return ResponseEntity.ok().body(result);
        } catch (Exception ex) {
            System.err.println("DEBUG (PetroleoRestController): Excepción en CargaMasiva: " + ex.getMessage());
            ex.printStackTrace();
            result.correct = false;
            result.errorMessage = "Error al realizar la carga masiva: " + ex.getLocalizedMessage();
            result.ex = ex;
            return ResponseEntity.internalServerError().body(result);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
    @GetMapping("/Usuarios")
    public ResponseEntity<Result<List<Usuario>>> getUsuarios() {
        System.out.println("DEBUG (PetroleoRestController): Accediendo a GET /Api/Usuarios");
        try {
            Result<List<Usuario>> result = usuarioDAOImplemetation.GetAll();
            System.out.println("DEBUG (PetroleoRestController): usuarioDAOImplemetation.GetAll() - Correct: " + result.correct);
            if (!result.correct) {
                System.err.println("DEBUG (PetroleoRestController): Error en DAO: " + result.errorMessage);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            System.err.println("DEBUG (PetroleoRestController): Excepción al obtener usuarios: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.error("Error interno del servidor al obtener usuarios."));
        }
    }

    // Este método 'procesar' se mantiene aquí porque interactúa directamente con los repositorios del controlador.
    private ResponseEntity<Result<Void>> procesar(ResultadoLectura resultadoLectura) {
        Result<Void> result = new Result<>();
        try {
            // Guardar zonas
            if (resultadoLectura.getZonas() != null) {
                zonaRepository.saveAll(resultadoLectura.getZonas());
            }

            // Guardar usuarios
            if (resultadoLectura.getUsuarios() != null) {
                usuarioRepository.saveAll(resultadoLectura.getUsuarios());
            }
            //Obtengo los usuarios de la bd para sacar su id
            List<Usuario> UsuariosGuardados = usuarioRepository.findAll();

            //Mapeo los usuarios para hacer una busqueda por nombre
            Map<String, Usuario> mapaUsuarios = UsuariosGuardados.stream()
                    .collect(Collectors.toMap(Usuario::getNombre, Function.identity(), (u1, u2) -> u1));

            //Asociar los a usuarios por nombre
            for (Contrato contrato : resultadoLectura.getContratos()) {
                String nombreUsuario = contrato.getUsuario().getNombre();
                Usuario usuarioConId = mapaUsuarios.get(nombreUsuario);
                contrato.setUsuario(usuarioConId);
            }
            //Guardar contratos
            if (resultadoLectura.getContratos() != null) {
                contratoRepository.saveAll(resultadoLectura.getContratos());
            }

            // Guardar nodos de recepción
            if (resultadoLectura.getNrecepciones() != null) {
                nodoRecepcionRepository.saveAll(resultadoLectura.getNrecepciones());
            }

            // Guardar nodos de entrega
            if (resultadoLectura.getNentregas() != null) {
                nodoEntregaRepository.saveAll(resultadoLectura.getNentregas());
            }

            // Guardar cantidades
            if (resultadoLectura.getCantidades() != null) {
                cantidadRepository.saveAll(resultadoLectura.getCantidades());
            }

            //Obtener los nodos de entrega guardados desde la bd
            List<NodoEntrega> NodosEntregaGuardados = nodoEntregaRepository.findAll();

            //Mapeo de los nodos para hacer una busqueda por clave
            Map<String, NodoEntrega> MapaEntrega = NodosEntregaGuardados.stream()
                    .collect(Collectors.toMap(NodoEntrega::getClave, Function.identity(), (u1, u2) -> u1));
            //Asociar a los nodos por clave
            for (Transaccion transaccion : resultadoLectura.getTransacciones()) {
                String ClaveEntrega = transaccion.getNodoEntrega().getClave();
                NodoEntrega NodoId = MapaEntrega.get(ClaveEntrega);
                transaccion.setNodoEntrega(NodoId);
            }
            //Id de nodo recepcion
            List<NodoRecepcion> NodosRecepcionGuardados = nodoRecepcionRepository.findAll();
            Map<String, NodoRecepcion> MapaRecepcion = NodosRecepcionGuardados.stream()
                    .collect(Collectors.toMap(NodoRecepcion::getClave, Function.identity(), (u1, u2) -> u1));

            for (Transaccion transaccion : resultadoLectura.getTransacciones()) {
                String ClaveRecepcion = transaccion.getNodoRecepcion().getClave();
                NodoRecepcion NodoId = MapaRecepcion.get(ClaveRecepcion);
                transaccion.setNodoRecepcion(NodoId);
            }

            //Zonas
            List<Zona> ZonasGuardadas = zonaRepository.findAll();
            Map<String, Zona> MapaZona = ZonasGuardadas.stream()
                    .collect(Collectors.toMap(Zona::getDescripcion, Function.identity(), (u1, u2) -> u1));

            for (Transaccion transaccion : resultadoLectura.getTransacciones()) {
                String ZonaIn = transaccion.getZonaInyeccion().getDescripcion();
                Zona ZonaInId = MapaZona.get(ZonaIn);
                transaccion.setZonaInyeccion(ZonaInId);
            }
            for (Transaccion transaccion : resultadoLectura.getTransacciones()) {
                String ZonaEx = transaccion.getZonaExtraccion().getDescripcion();
                Zona ZonaExId = MapaZona.get(ZonaEx);
                transaccion.setZonaExtraccion(ZonaExId);
            }

            //Contratos
            List<Contrato> ContratosGuardados = contratoRepository.findAll();
            Map<String, Contrato> MapaContrato = ContratosGuardados.stream()
                    .collect(Collectors.toMap(Contrato::getClaveContrato, Function.identity(), (u1, u2) -> u1));

            for (Transaccion transaccion : resultadoLectura.getTransacciones()) {
                String ClaveContrato = transaccion.getContrato().getClaveContrato();
                Contrato ContratoId = MapaContrato.get(ClaveContrato);
                transaccion.setContrato(ContratoId);
            }

            // Guardar Transacciones
            if (resultadoLectura.getTransacciones() != null) {
                transaccionRepository.saveAll(resultadoLectura.getTransacciones());
            }

            //Guardar Tarifas
            if (resultadoLectura.getTarifas() != null) {
                tarifaRepository.saveAll(resultadoLectura.getTarifas());
            }

            result.correct = true;
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            ex.printStackTrace();
            result.correct = false;
            result.errorMessage = "Error al procesar el archivo: " + ex.getLocalizedMessage();
            result.ex = ex;
            return ResponseEntity.internalServerError().body(result);
        }
    }

    @GetMapping("/TrsaccionGetAll/{IdContrato}")
    public Result transaccionGetnyId(@PathVariable int IdContrato) {
        Result result = transaccionDAOImplemetation.getByContratoId(IdContrato);
        return result;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
    @GetMapping("/paginado")
    public ResponseEntity<Map<String, Object>> getPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Result paginatedResult = transaccionDAOImplemetation.getPaginated(page, size);
        long total = transaccionDAOImplemetation.getTotalCount();

        if (!paginatedResult.correct) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("data", paginatedResult.object);
        response.put("currentPage", page);
        response.put("totalItems", total);
        response.put("totalPages", (int) Math.ceil((double) total / size));
        return ResponseEntity.ok(response);
    }

}
