package com.CRamirezEvaluacionContratosPetroleo.DAO;

import com.CRamirezEvaluacionContratosPetroleo.JPA.Result;
import com.CRamirezEvaluacionContratosPetroleo.JPA.Usuario;
import com.CRamirezEvaluacionContratosPetroleo.JPA.Rol; // Importar la clase Rol
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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

            @SuppressWarnings("unchecked")
            List<Object[]> datos = query.getResultList();

            List<Usuario> usuarios = new ArrayList<>();

            for (Object[] row : datos) {
                Usuario us = new Usuario();

                // Mapeo de las 5 columnas del procedimiento almacenado
                us.setIdUsuario(((BigDecimal) row[0]).intValue());
                us.setNombre((String) row[1]);
                us.setUserName((String) row[2]); // Mapea la columna UserName

                // Mapea el Rol
                // El procedimiento devuelve el nombre del rol (NombreRol), no el objeto Rol completo.
                // Creamos un objeto Rol y le asignamos solo el nombre.
                Rol rol = new Rol();
                rol.setRol((String) row[3]); // Mapea la columna NombreRol
                us.setRol(rol);

                // Mapea el Status
                // Dependiendo del tipo de dato de Status en tu BD, podría ser BigDecimal o Integer
                if (row[4] instanceof BigDecimal) {
                    us.setStatus(((BigDecimal) row[4]).intValue());
                } else if (row[4] instanceof Integer) {
                    us.setStatus((Integer) row[4]);
                } else {
                    // Manejar otros tipos si es necesario o asignar un valor por defecto
                    us.setStatus(0); // Por defecto 'Inactivo' si no se puede mapear
                    System.err.println("DEBUG: Tipo de dato inesperado para Status: " + row[4].getClass().getName());
                }

                usuarios.add(us);
            }
            result.correct = true;
            result.object = usuarios;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = ex;
            System.err.println("Error en UsuarioDAOImplemetation.GetAll(): " + ex.getMessage());
            ex.printStackTrace();
        }
        return result;
    }
}
