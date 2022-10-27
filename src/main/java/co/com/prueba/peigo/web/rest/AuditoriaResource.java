package co.com.prueba.peigo.web.rest;

import co.com.prueba.peigo.repository.AuditoriaRepository;
import co.com.prueba.peigo.service.AuditoriaService;
import co.com.prueba.peigo.service.dto.AuditoriaDTO;
import co.com.prueba.peigo.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link co.com.prueba.peigo.domain.Auditoria}.
 */
@RestController
@RequestMapping("/api")
public class AuditoriaResource {

    private final Logger log = LoggerFactory.getLogger(AuditoriaResource.class);

    private static final String ENTITY_NAME = "auditoria";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AuditoriaService auditoriaService;

    private final AuditoriaRepository auditoriaRepository;

    public AuditoriaResource(AuditoriaService auditoriaService, AuditoriaRepository auditoriaRepository) {
        this.auditoriaService = auditoriaService;
        this.auditoriaRepository = auditoriaRepository;
    }

    /**
     * {@code POST  /auditorias} : Create a new auditoria.
     *
     * @param auditoriaDTO the auditoriaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new auditoriaDTO, or with status {@code 400 (Bad Request)} if the auditoria has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/auditorias")
    public ResponseEntity<AuditoriaDTO> createAuditoria(@RequestBody AuditoriaDTO auditoriaDTO) throws URISyntaxException {
        log.debug("REST request to save Auditoria : {}", auditoriaDTO);
        if (auditoriaDTO.getId() != null) {
            throw new BadRequestAlertException("A new auditoria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AuditoriaDTO result = auditoriaService.save(auditoriaDTO);
        return ResponseEntity
            .created(new URI("/api/auditorias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /auditorias/:id} : Updates an existing auditoria.
     *
     * @param id the id of the auditoriaDTO to save.
     * @param auditoriaDTO the auditoriaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated auditoriaDTO,
     * or with status {@code 400 (Bad Request)} if the auditoriaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the auditoriaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/auditorias/{id}")
    public ResponseEntity<AuditoriaDTO> updateAuditoria(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AuditoriaDTO auditoriaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Auditoria : {}, {}", id, auditoriaDTO);
        if (auditoriaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, auditoriaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!auditoriaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AuditoriaDTO result = auditoriaService.update(auditoriaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, auditoriaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /auditorias/:id} : Partial updates given fields of an existing auditoria, field will ignore if it is null
     *
     * @param id the id of the auditoriaDTO to save.
     * @param auditoriaDTO the auditoriaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated auditoriaDTO,
     * or with status {@code 400 (Bad Request)} if the auditoriaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the auditoriaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the auditoriaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/auditorias/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AuditoriaDTO> partialUpdateAuditoria(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AuditoriaDTO auditoriaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Auditoria partially : {}, {}", id, auditoriaDTO);
        if (auditoriaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, auditoriaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!auditoriaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AuditoriaDTO> result = auditoriaService.partialUpdate(auditoriaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, auditoriaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /auditorias} : get all the auditorias.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of auditorias in body.
     */
    @GetMapping("/auditorias")
    public List<AuditoriaDTO> getAllAuditorias() {
        log.debug("REST request to get all Auditorias");
        return auditoriaService.findAll();
    }

    /**
     * {@code GET  /auditorias/:id} : get the "id" auditoria.
     *
     * @param id the id of the auditoriaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the auditoriaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/auditorias/{id}")
    public ResponseEntity<AuditoriaDTO> getAuditoria(@PathVariable Long id) {
        log.debug("REST request to get Auditoria : {}", id);
        Optional<AuditoriaDTO> auditoriaDTO = auditoriaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(auditoriaDTO);
    }

    /**
     * {@code DELETE  /auditorias/:id} : delete the "id" auditoria.
     *
     * @param id the id of the auditoriaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/auditorias/{id}")
    public ResponseEntity<Void> deleteAuditoria(@PathVariable Long id) {
        log.debug("REST request to delete Auditoria : {}", id);
        auditoriaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
