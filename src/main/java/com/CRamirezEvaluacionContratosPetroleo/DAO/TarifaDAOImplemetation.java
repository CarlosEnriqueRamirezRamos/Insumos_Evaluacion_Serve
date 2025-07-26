package com.CRamirezEvaluacionContratosPetroleo.DAO;

import com.CRamirezEvaluacionContratosPetroleo.JPA.Result;
import com.CRamirezEvaluacionContratosPetroleo.JPA.Tarifa;
import com.CRamirezEvaluacionContratosPetroleo.JPA.Transaccion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TarifaDAOImplemetation implements ITaridaDAO {

    @Autowired
    public JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result GetAll(int idTransaccion) {
        Result result = new Result();
        try {
            StoredProcedureQuery query = entityManager
                    .createStoredProcedureQuery("GET_TARIFA");

            query.registerStoredProcedureParameter("p_transaccion_id", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_cursor", Class.class, ParameterMode.REF_CURSOR);

            query.setParameter("p_transaccion_id", idTransaccion);
            query.execute();

            @SuppressWarnings("unchecked")
            List<Object[]> datos = query.getResultList();
            List<Tarifa> tarifas = new ArrayList<>();

            for (Object[] row : datos) {
                Tarifa tarifa = new Tarifa();
                tarifa.setIdTarifa(((BigDecimal) row[0]).intValue());
                tarifa.setExcesoFirme(row[1] != null ? ((BigDecimal) row[1]).doubleValue() : null);
                tarifa.setUsoInterrumpible(row[2] != null ? ((BigDecimal) row[2]).intValue() : null);

                Transaccion transaccion = new Transaccion();
                transaccion.setIdtransaccion(((BigDecimal) row[3]).intValue());
                
                tarifa.setTransaccion(transaccion);
                tarifas.add(tarifa);
            }

            result.correct = true;
            result.object = tarifas;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = ex;
        }
        return result;
    }
}
