
package com.CRamirezEvaluacionContratosPetroleo.Repository;

import com.CRamirezEvaluacionContratosPetroleo.JPA.Cantidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CantidadRepository extends JpaRepository<Cantidad, Long> {
    
}
