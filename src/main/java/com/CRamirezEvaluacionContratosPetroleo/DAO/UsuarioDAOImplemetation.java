package com.CRamirezEvaluacionContratosPetroleo.DAO;

import com.CRamirezEvaluacionContratosPetroleo.JPA.Result;
import com.CRamirezEvaluacionContratosPetroleo.JPA.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioDAOImplemetation implements IUsuarioDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result GetAll() {
        Result result = new Result();
        try {
            StoredProcedureQuery query = entityManager.
                    createStoredProcedureQuery("GET_ALL_USUARIO");

            query.registerStoredProcedureParameter(1, void.class, ParameterMode.REF_CURSOR);
            query.execute();

            @SuppressWarnings("uncecked")
            List<Object[]> datos = query.getResultList();

            List<Usuario> usuarios = new ArrayList<>();

            for (Object[] row : datos) {
                Usuario us = new Usuario();

                us.setIdUsuario(((BigDecimal) row[0]).intValue());
                us.setNombre((String) row[1]);

                usuarios.add(us);
            }
            result.correct = true;
            result.object = usuarios;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = ex;
        }
        return result;
    }
}
