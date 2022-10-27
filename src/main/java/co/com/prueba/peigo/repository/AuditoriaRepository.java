package co.com.prueba.peigo.repository;

import co.com.prueba.peigo.domain.Auditoria;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Auditoria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuditoriaRepository extends JpaRepository<Auditoria, Long> {}
