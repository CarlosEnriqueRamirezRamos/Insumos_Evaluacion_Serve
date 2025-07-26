package com.CRamirezEvaluacionContratosPetroleo.DAO;

import com.CRamirezEvaluacionContratosPetroleo.JPA.Contrato;
import com.CRamirezEvaluacionContratosPetroleo.JPA.Result;
import com.CRamirezEvaluacionContratosPetroleo.JPA.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ContratosDAOImplemetation implements IContratosDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result Getbyid(int IdUsuario) {
        Result result = new Result();

        try {
            // Llamada al procedimiento almacenado
            StoredProcedureQuery query = entityManager
                    .createStoredProcedureQuery("GET_CONTRATOS")
                    .registerStoredProcedureParameter("p_Usuarios_id", Integer.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("p_Cursor", Class.class, ParameterMode.REF_CURSOR)
                    .setParameter("p_Usuarios_id", IdUsuario);

            query.execute();

            // Procesar resultados
            @SuppressWarnings("unchecked")
            List<Object[]> results = query.getResultList();
            List<Contrato> contratos = new ArrayList<>();

            for (Object[] row : results) {
                Contrato contrato = new Contrato();
                Usuario usuario = new Usuario();

                // Mapeo de resultados
                contrato.setIdContrato(((Number) row[0]).intValue());
                contrato.setClaveContrato((String) row[1]);

                usuario.setIdUsuario(((Number) row[2]).intValue());
                usuario.setNombre((String) row[3]);

                // Establecer relación
                contrato.setUsuario(usuario);

                contratos.add(contrato);
            }

            result.correct = true;
            result.object = contratos;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = ex;
        }

        return result;
    }
}
