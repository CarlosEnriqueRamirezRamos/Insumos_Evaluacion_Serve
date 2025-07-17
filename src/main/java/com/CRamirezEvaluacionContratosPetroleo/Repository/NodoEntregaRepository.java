package com.CRamirezEvaluacionContratosPetroleo.Repository;

import com.CRamirezEvaluacionContratosPetroleo.JPA.NodoEntrega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NodoEntregaRepository extends JpaRepository<NodoEntrega, Long>{
    
}
