
package com.CRamirezEvaluacionContratosPetroleo.Repository;

import com.CRamirezEvaluacionContratosPetroleo.JPA.NodoRecepcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodoRecepcionRepository extends JpaRepository<NodoRecepcion, Long>{
    
}
