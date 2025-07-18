package com.CRamirezEvaluacionContratosPetroleo.DAO;

import com.CRamirezEvaluacionContratosPetroleo.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TransaccionDAOImplemetation implements ITransaccionDAO {

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

                dto.setGasExceso(row[10] != null ? ((BigDecimal) row[10]).doubleValue() : null);
                dto.setCargoUso(row[11] != null ? ((BigDecimal) row[11]).doubleValue() : null);
                dto.setCargoGasExceso(row[12] != null ? ((BigDecimal) row[12]).doubleValue() : null);
                dto.setFacturaTotal(row[13] != null ? ((BigDecimal) row[13]).doubleValue() : null);
                dto.setNominadaRecepcion(row[14] != null ? ((BigDecimal) row[14]).doubleValue() : null);
                dto.setAsignadaRecepcion(row[15] != null ? ((BigDecimal) row[15]).doubleValue() : null);
                dto.setNominadaEntrega(row[16] != null ? ((BigDecimal) row[16]).doubleValue() : null);
                dto.setAsignadaEntrega(row[17] != null ? ((BigDecimal) row[17]).doubleValue() : null);
                dto.setExcesoFirme(row[18] != null ? ((BigDecimal) row[18]).doubleValue() : null);
                dto.setUsoInterrumpible(row[19] != null ? ((BigDecimal) row[19]).doubleValue() : null);

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

}
