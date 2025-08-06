package com.CRamirezEvaluacionContratosPetroleo.DAO;

import com.CRamirezEvaluacionContratosPetroleo.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TransaccionDAOImplemetation implements ITransaccionDAO {

    @Autowired
    public JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result GetAll() {
        Result result = new Result();
        try {
            StoredProcedureQuery query = entityManager
                    .createStoredProcedureQuery("GET_ALL_TRANSACCIONES");

            query.registerStoredProcedureParameter(1, void.class, ParameterMode.REF_CURSOR);

            query.execute();

            @SuppressWarnings("unchecked")
            List<Object[]> datos = query.getResultList();

            List<TransaccionDTO> transacciones = new ArrayList<>();

            for (Object[] row : datos) {
                TransaccionDTO dto = new TransaccionDTO();

                dto.setIdTransaccion(((BigDecimal) row[0]).intValue());
                dto.setFechaRegistro((Date) row[1]);
                dto.setNombreUsuario((String) row[2]);
                dto.setClaveContrato((String) row[3]);
                dto.setClaveNodoRecepcion((String) row[4]);
                dto.setDescNodoRecepcion((String) row[5]);
                dto.setClaveNodoEntrega((String) row[6]);
                dto.setDescNodoEntrega((String) row[7]);
                dto.setZonaInyeccion((String) row[8]);
                dto.setZonaExtraccion((String) row[9]);

                // --- ¡CORRECCIÓN AQUÍ! Asignar 0.0 si el valor es null en la base de datos ---
                dto.setGasExceso(row[10] != null ? ((BigDecimal) row[10]).doubleValue() : 0.0);
                dto.setCargoUso(row[11] != null ? ((BigDecimal) row[11]).doubleValue() : 0.0);
                dto.setCargoGasExceso(row[12] != null ? ((BigDecimal) row[12]).doubleValue() : 0.0);
                dto.setFacturaTotal(row[13] != null ? ((BigDecimal) row[13]).doubleValue() : 0.0);
                dto.setNominadaRecepcion(row[14] != null ? ((BigDecimal) row[14]).doubleValue() : 0.0);
                dto.setAsignadaRecepcion(row[15] != null ? ((BigDecimal) row[15]).doubleValue() : 0.0);
                dto.setNominadaEntrega(row[16] != null ? ((BigDecimal) row[16]).doubleValue() : 0.0);
                dto.setAsignadaEntrega(row[17] != null ? ((BigDecimal) row[17]).doubleValue() : 0.0);
                dto.setExcesoFirme(row[18] != null ? ((BigDecimal) row[18]).doubleValue() : 0.0);
                dto.setUsoInterrumpible(row[19] != null ? ((BigDecimal) row[19]).doubleValue() : 0.0);
                // --- FIN DE CORRECCIÓN ---

                transacciones.add(dto);
            }

            result.correct = true;
            result.object = transacciones;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result getByContratoId(int idContrato) {
        Result result = new Result();
        List<Object> transacciones = new ArrayList<>();

        try {
            // 1. Verificar si existe el contrato
            Integer countContrato = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM CONTRATO WHERE IdContrato = ?",
                    Integer.class,
                    idContrato
            );

            if (countContrato == 0) {
                result.correct = false;
                result.errorMessage = "No existe un contrato con ID: " + idContrato;
                return result;
            }

            // 2. Verificar si hay transacciones para ese contrato
            Integer countTransacciones = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM TRANSACCION WHERE IdContrato = ?",
                    Integer.class,
                    idContrato
            );

            if (countTransacciones == 0) {
                result.correct = true; // Considerar esto como caso válido
                result.errorMessage = "No hay transacciones para el contrato ID: " + idContrato;
                return result;
            }

            // 3. Ejecutar el procedimiento almacenado
            Map<String, Object> out = jdbcTemplate.call(
                    connection -> {
                        CallableStatement cs = connection.prepareCall(
                                "{ call GET_TRANSACCIONES_POR_CONTRATO(?, ?) }"
                        );
                        cs.registerOutParameter(1, -10); // OracleTypes.CURSOR
                        cs.setInt(2, idContrato);
                        return cs;
                    },
                    Collections.emptyList()
            );

            // 4. Procesar resultados
            ResultSet rs = (ResultSet) out.get("1");
            if (rs != null) {
                try {
                    while (rs.next()) {
                        Map<String, Object> transaccion = new LinkedHashMap<>();
                        transaccion.put("idTransaccion", rs.getInt("ID_TRANSACCION"));
                        transaccion.put("fechaRegistro", rs.getString("FECHA_REGISTRO"));
                        transaccion.put("claveContrato", rs.getString("CLAVE_CONTRATO"));
                        transaccion.put("zonaInyeccion", rs.getInt("ZONA_INYECCION"));
                        transaccion.put("zonaExtraccion", rs.getInt("ZONA_EXTRACCION"));
                        transaccion.put("gasExceso", rs.getDouble("GAS_EXCESO"));
                        transaccion.put("cargoUso", rs.getDouble("CARGO_USO"));
                        transaccion.put("cargoGas", rs.getDouble("CARGO_GAS"));
                        transaccion.put("totalFactura", rs.getDouble("TOTAL_FACTURA"));
                        transacciones.add(transaccion);
                    }
                } finally {
                    try {
                        if (rs != null) {
                            rs.close();
                        }
                    } catch (SQLException e) {
                    }
                }
            }

            result.correct = true;
            result.objects = transacciones;
            result.errorMessage = transacciones.isEmpty()
                    ? "No se encontraron transacciones"
                    : transacciones.size() + " transacciones encontradas";

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = "Error al consultar transacciones: " + e.getMessage();
            result.ex = e;
        }

        return result;
    }

    // Agrega esto en tu clase existente TransaccionDAOImplemetation
    public Result getPaginated(int page, int size) {
        Result result = new Result();
        try {
            // Usa tu método GetAll existente
            Result allData = this.GetAll();

            if (!allData.correct) {
                result.correct = false;
                result.errorMessage = allData.errorMessage;
                return result;
            }

            List<TransaccionDTO> allTransacciones = (List<TransaccionDTO>) allData.object;
            int start = page * size;
            int end = Math.min(start + size, allTransacciones.size());

            result.object = allTransacciones.subList(start, end);
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
        }
        return result;
    }

    public long getTotalCount() {
        try {
            Result allData = this.GetAll();
            return allData.correct ? ((List<?>) allData.object).size() : 0;
        } catch (Exception ex) {
            return 0;
        }
    }
}
