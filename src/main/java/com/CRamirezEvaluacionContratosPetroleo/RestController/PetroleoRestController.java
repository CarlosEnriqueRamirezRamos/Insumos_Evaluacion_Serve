package com.CRamirezEvaluacionContratosPetroleo.RestController;

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
import com.CRamirezEvaluacionContratosPetroleo.Repository.NodoRecepcionRepository;
import com.CRamirezEvaluacionContratosPetroleo.Repository.NodoEntregaRepository;
import com.CRamirezEvaluacionContratosPetroleo.Repository.TarifaRepository;
import com.CRamirezEvaluacionContratosPetroleo.Repository.TransaccionRepository;
import com.CRamirezEvaluacionContratosPetroleo.Repository.UsuarioRepository;
import com.CRamirezEvaluacionContratosPetroleo.Repository.ZonaRepository;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/CargaMasiva")
public class PetroleoRestController {

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

    public ResultadoLectura LecturaArchivo(File archivo) {
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

        try (XSSFWorkbook workbook = new XSSFWorkbook(archivo);) {
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
                    contrato.usuario = new Usuario();
                    String ClaveContrato = row.getCell(1).getStringCellValue();
                    if (!ContratoUnico.contains(ClaveContrato)) {
                        ContratoUnico.add(ClaveContrato);
                        contrato.setClaveContrato(ClaveContrato);
                        contrato.usuario.setNombre(row.getCell(2).getStringCellValue());
                        listaContratos.add(contrato);
                    }

                    //Transaccion
                    Transaccion transaccion = new Transaccion();
                    transaccion.contrato = new Contrato();
                    transaccion.nodoRecepcion = new NodoRecepcion();
                    transaccion.nodoEntrega = new NodoEntrega();
                    transaccion.zonaInyeccion = new Zona();
                    transaccion.zonaExtraccion = new Zona();
                    transaccion.setFechaRegistro(row.getCell(0).getDateCellValue());
                    transaccion.contrato.setClaveContrato(ClaveContrato);
                    transaccion.nodoRecepcion.setClave(ClaveRecepcion);
                    transaccion.nodoEntrega.setClave(ClaveEntrega);
                    transaccion.zonaInyeccion.setDescripcion(ZonaInyeccion);
                    transaccion.zonaExtraccion.setDescripcion(ZonaExtraccion);
                    transaccion.setGasExceso(row.getCell(13).getNumericCellValue());
                    transaccion.setCargoUso(row.getCell(16).getNumericCellValue());
                    transaccion.setCargoGasExceso(row.getCell(17).getNumericCellValue());
                    transaccion.setFacturaTotal(row.getCell(18).getNumericCellValue());
                    transaccion.setCantidad(cantidad);
                    listaTransacciones.add(transaccion);
                    
                    //Tarifa
                    Tarifa tarifa = new Tarifa();
                    tarifa.transaccion = new Transaccion();
                    
                    tarifa.setExcesoFirme(row.getCell(14).getNumericCellValue());
                    tarifa.setUsoInterrumpible(row.getCell(15).getNumericCellValue());
                    tarifa.setTransaccion(transaccion);
                    listaTarifas.add(tarifa);
                }
            }

        } catch (Exception ex) {
            System.out.println("Hubo un error quien sabe donde");

            listaZonas = null;
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

    @PostMapping
    public ResponseEntity CargaMasiva(MultipartFile archivo) {
        Result result = new Result();
        try {
            if (archivo != null && !archivo.isEmpty()) {
                String tipoArchivo = archivo.getOriginalFilename().split("\\.")[1];

                String root = System.getProperty("user.dir");
                String path = "src/main/resources/static/Archivos";
                String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy-MM-dd   HH-mm-SS"));
                String absolutePath = root + "/" + path + "/" + fecha + archivo.getOriginalFilename();
                archivo.transferTo(new File(absolutePath));

                //Leer archivo
                ResultadoLectura resultadoLectura = LecturaArchivo(new File(absolutePath));
                List<Zona> listaZonas = resultadoLectura.getZonas();
                List<Usuario> listaUsuarios = resultadoLectura.getUsuarios();
                List<NodoRecepcion> listaNodoRecepcions = resultadoLectura.getNrecepciones();
                List<NodoEntrega> listaNodoEntregas = resultadoLectura.getNentregas();
                List<Cantidad> listaCantidades = resultadoLectura.getcantidades();
                List<Contrato> listaContratos = resultadoLectura.getcontratos();
                List<Transaccion> listaTransacciones = resultadoLectura.getTransacciones();
                List<Tarifa> listaTarifas = resultadoLectura.getTarifas();

                procesar(resultadoLectura);
                System.out.println("");

            }
            result.correct = true;
        } catch (Exception ex) {
            ex.getLocalizedMessage();
            result.correct = false;
        }

        return ResponseEntity.ok().body(result);
    }

    public ResponseEntity procesar(ResultadoLectura resultadoLectura) {
        Result result = new Result();
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
            for (Contrato contrato : resultadoLectura.getcontratos()) {
                String nombreUsuario = contrato.getUsuario().getNombre(); //Se saca el nombre de excel
                Usuario usuarioConId = mapaUsuarios.get(nombreUsuario);
                contrato.setUsuario(usuarioConId);
            }
            //Guardar contratos
            if (resultadoLectura.getcontratos() != null) {
                contratoRepository.saveAll(resultadoLectura.getcontratos());
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
            if (resultadoLectura.getcantidades() != null) {
                cantidadRepository.saveAll(resultadoLectura.getcantidades());
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
            if(resultadoLectura.getTarifas() != null){
                tarifaRepository.saveAll(resultadoLectura.getTarifas());
            }

            result.correct = true;
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            ex.printStackTrace();
            result.correct = false;
            return ResponseEntity.internalServerError().body(result);
        }
    }

}
