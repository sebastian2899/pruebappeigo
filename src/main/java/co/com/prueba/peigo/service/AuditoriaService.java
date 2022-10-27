package co.com.prueba.peigo.service;

import co.com.prueba.peigo.service.dto.AuditoriaDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link co.com.prueba.peigo.domain.Auditoria}.
 */
public interface AuditoriaService {
    /**
     * Save a auditoria.
     *
     * @param auditoriaDTO the entity to save.
     * @return the persisted entity.
     */
    AuditoriaDTO save(AuditoriaDTO auditoriaDTO);

    /**
     * Updates a auditoria.
     *
     * @param auditoriaDTO the entity to update.
     * @return the persisted entity.
     */
    AuditoriaDTO update(AuditoriaDTO auditoriaDTO);

    /**
     * Partially updates a auditoria.
     *
     * @param auditoriaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AuditoriaDTO> partialUpdate(AuditoriaDTO auditoriaDTO);

    /**
     * Get all the auditorias.
     *
     * @return the list of entities.
     */
    List<AuditoriaDTO> findAll();

    /**
     * Get the "id" auditoria.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AuditoriaDTO> findOne(Long id);

    /**
     * Delete the "id" auditoria.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
