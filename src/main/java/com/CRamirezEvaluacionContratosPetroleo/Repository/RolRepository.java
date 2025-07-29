package com.CRamirezEvaluacionContratosPetroleo.Repository;

import com.CRamirezEvaluacionContratosPetroleo.JPA.Rol; // <-- Importa tu entidad Rol
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> { // <-- Asegúrate que el segundo tipo sea Integer si IdRol es int
}