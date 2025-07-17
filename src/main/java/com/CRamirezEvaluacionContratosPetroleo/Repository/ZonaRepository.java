package com.CRamirezEvaluacionContratosPetroleo.Repository;

import com.CRamirezEvaluacionContratosPetroleo.JPA.Zona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ZonaRepository extends JpaRepository<Zona, Long> {
    
}
