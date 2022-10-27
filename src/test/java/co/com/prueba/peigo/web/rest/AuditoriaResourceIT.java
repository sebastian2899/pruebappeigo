package co.com.prueba.peigo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.com.prueba.peigo.IntegrationTest;
import co.com.prueba.peigo.domain.Auditoria;
import co.com.prueba.peigo.repository.AuditoriaRepository;
import co.com.prueba.peigo.service.dto.AuditoriaDTO;
import co.com.prueba.peigo.service.mapper.AuditoriaMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AuditoriaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AuditoriaResourceIT {

    private static final Long DEFAULT_USUARIO_CREACION = 1L;
    private static final Long UPDATED_USUARIO_CREACION = 2L;

    private static final Instant DEFAULT_FECHA_CREACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_CREACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_USUARIO_MODIFICACION = 1L;
    private static final Long UPDATED_USUARIO_MODIFICACION = 2L;

    private static final Instant DEFAULT_FECHA_MODIFICACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_MODIFICACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_REQUEST = "AAAAAAAAAA";
    private static final String UPDATED_REQUEST = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSE = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/auditorias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AuditoriaRepository auditoriaRepository;

    @Autowired
    private AuditoriaMapper auditoriaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAuditoriaMockMvc;

    private Auditoria auditoria;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Auditoria createEntity(EntityManager em) {
        Auditoria auditoria = new Auditoria()
            .usuarioCreacion(DEFAULT_USUARIO_CREACION)
            .fechaCreacion(DEFAULT_FECHA_CREACION)
            .usuarioModificacion(DEFAULT_USUARIO_MODIFICACION)
            .fechaModificacion(DEFAULT_FECHA_MODIFICACION)
            .request(DEFAULT_REQUEST)
            .response(DEFAULT_RESPONSE);
        return auditoria;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Auditoria createUpdatedEntity(EntityManager em) {
        Auditoria auditoria = new Auditoria()
            .usuarioCreacion(UPDATED_USUARIO_CREACION)
            .fechaCreacion(UPDATED_FECHA_CREACION)
            .usuarioModificacion(UPDATED_USUARIO_MODIFICACION)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION)
            .request(UPDATED_REQUEST)
            .response(UPDATED_RESPONSE);
        return auditoria;
    }

    @BeforeEach
    public void initTest() {
        auditoria = createEntity(em);
    }

    @Test
    @Transactional
    void createAuditoria() throws Exception {
        int databaseSizeBeforeCreate = auditoriaRepository.findAll().size();
        // Create the Auditoria
        AuditoriaDTO auditoriaDTO = auditoriaMapper.toDto(auditoria);
        restAuditoriaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(auditoriaDTO)))
            .andExpect(status().isCreated());

        // Validate the Auditoria in the database
        List<Auditoria> auditoriaList = auditoriaRepository.findAll();
        assertThat(auditoriaList).hasSize(databaseSizeBeforeCreate + 1);
        Auditoria testAuditoria = auditoriaList.get(auditoriaList.size() - 1);
        assertThat(testAuditoria.getUsuarioCreacion()).isEqualTo(DEFAULT_USUARIO_CREACION);
        assertThat(testAuditoria.getFechaCreacion()).isEqualTo(DEFAULT_FECHA_CREACION);
        assertThat(testAuditoria.getUsuarioModificacion()).isEqualTo(DEFAULT_USUARIO_MODIFICACION);
        assertThat(testAuditoria.getFechaModificacion()).isEqualTo(DEFAULT_FECHA_MODIFICACION);
        assertThat(testAuditoria.getRequest()).isEqualTo(DEFAULT_REQUEST);
        assertThat(testAuditoria.getResponse()).isEqualTo(DEFAULT_RESPONSE);
    }

    @Test
    @Transactional
    void createAuditoriaWithExistingId() throws Exception {
        // Create the Auditoria with an existing ID
        auditoria.setId(1L);
        AuditoriaDTO auditoriaDTO = auditoriaMapper.toDto(auditoria);

        int databaseSizeBeforeCreate = auditoriaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuditoriaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(auditoriaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Auditoria in the database
        List<Auditoria> auditoriaList = auditoriaRepository.findAll();
        assertThat(auditoriaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAuditorias() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList
        restAuditoriaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auditoria.getId().intValue())))
            .andExpect(jsonPath("$.[*].usuarioCreacion").value(hasItem(DEFAULT_USUARIO_CREACION.intValue())))
            .andExpect(jsonPath("$.[*].fechaCreacion").value(hasItem(DEFAULT_FECHA_CREACION.toString())))
            .andExpect(jsonPath("$.[*].usuarioModificacion").value(hasItem(DEFAULT_USUARIO_MODIFICACION.intValue())))
            .andExpect(jsonPath("$.[*].fechaModificacion").value(hasItem(DEFAULT_FECHA_MODIFICACION.toString())))
            .andExpect(jsonPath("$.[*].request").value(hasItem(DEFAULT_REQUEST)))
            .andExpect(jsonPath("$.[*].response").value(hasItem(DEFAULT_RESPONSE)));
    }

    @Test
    @Transactional
    void getAuditoria() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get the auditoria
        restAuditoriaMockMvc
            .perform(get(ENTITY_API_URL_ID, auditoria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(auditoria.getId().intValue()))
            .andExpect(jsonPath("$.usuarioCreacion").value(DEFAULT_USUARIO_CREACION.intValue()))
            .andExpect(jsonPath("$.fechaCreacion").value(DEFAULT_FECHA_CREACION.toString()))
            .andExpect(jsonPath("$.usuarioModificacion").value(DEFAULT_USUARIO_MODIFICACION.intValue()))
            .andExpect(jsonPath("$.fechaModificacion").value(DEFAULT_FECHA_MODIFICACION.toString()))
            .andExpect(jsonPath("$.request").value(DEFAULT_REQUEST))
            .andExpect(jsonPath("$.response").value(DEFAULT_RESPONSE));
    }

    @Test
    @Transactional
    void getNonExistingAuditoria() throws Exception {
        // Get the auditoria
        restAuditoriaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAuditoria() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        int databaseSizeBeforeUpdate = auditoriaRepository.findAll().size();

        // Update the auditoria
        Auditoria updatedAuditoria = auditoriaRepository.findById(auditoria.getId()).get();
        // Disconnect from session so that the updates on updatedAuditoria are not directly saved in db
        em.detach(updatedAuditoria);
        updatedAuditoria
            .usuarioCreacion(UPDATED_USUARIO_CREACION)
            .fechaCreacion(UPDATED_FECHA_CREACION)
            .usuarioModificacion(UPDATED_USUARIO_MODIFICACION)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION)
            .request(UPDATED_REQUEST)
            .response(UPDATED_RESPONSE);
        AuditoriaDTO auditoriaDTO = auditoriaMapper.toDto(updatedAuditoria);

        restAuditoriaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, auditoriaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(auditoriaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Auditoria in the database
        List<Auditoria> auditoriaList = auditoriaRepository.findAll();
        assertThat(auditoriaList).hasSize(databaseSizeBeforeUpdate);
        Auditoria testAuditoria = auditoriaList.get(auditoriaList.size() - 1);
        assertThat(testAuditoria.getUsuarioCreacion()).isEqualTo(UPDATED_USUARIO_CREACION);
        assertThat(testAuditoria.getFechaCreacion()).isEqualTo(UPDATED_FECHA_CREACION);
        assertThat(testAuditoria.getUsuarioModificacion()).isEqualTo(UPDATED_USUARIO_MODIFICACION);
        assertThat(testAuditoria.getFechaModificacion()).isEqualTo(UPDATED_FECHA_MODIFICACION);
        assertThat(testAuditoria.getRequest()).isEqualTo(UPDATED_REQUEST);
        assertThat(testAuditoria.getResponse()).isEqualTo(UPDATED_RESPONSE);
    }

    @Test
    @Transactional
    void putNonExistingAuditoria() throws Exception {
        int databaseSizeBeforeUpdate = auditoriaRepository.findAll().size();
        auditoria.setId(count.incrementAndGet());

        // Create the Auditoria
        AuditoriaDTO auditoriaDTO = auditoriaMapper.toDto(auditoria);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuditoriaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, auditoriaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(auditoriaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Auditoria in the database
        List<Auditoria> auditoriaList = auditoriaRepository.findAll();
        assertThat(auditoriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAuditoria() throws Exception {
        int databaseSizeBeforeUpdate = auditoriaRepository.findAll().size();
        auditoria.setId(count.incrementAndGet());

        // Create the Auditoria
        AuditoriaDTO auditoriaDTO = auditoriaMapper.toDto(auditoria);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuditoriaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(auditoriaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Auditoria in the database
        List<Auditoria> auditoriaList = auditoriaRepository.findAll();
        assertThat(auditoriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAuditoria() throws Exception {
        int databaseSizeBeforeUpdate = auditoriaRepository.findAll().size();
        auditoria.setId(count.incrementAndGet());

        // Create the Auditoria
        AuditoriaDTO auditoriaDTO = auditoriaMapper.toDto(auditoria);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuditoriaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(auditoriaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Auditoria in the database
        List<Auditoria> auditoriaList = auditoriaRepository.findAll();
        assertThat(auditoriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAuditoriaWithPatch() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        int databaseSizeBeforeUpdate = auditoriaRepository.findAll().size();

        // Update the auditoria using partial update
        Auditoria partialUpdatedAuditoria = new Auditoria();
        partialUpdatedAuditoria.setId(auditoria.getId());

        partialUpdatedAuditoria.usuarioModificacion(UPDATED_USUARIO_MODIFICACION).fechaModificacion(UPDATED_FECHA_MODIFICACION);

        restAuditoriaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAuditoria.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAuditoria))
            )
            .andExpect(status().isOk());

        // Validate the Auditoria in the database
        List<Auditoria> auditoriaList = auditoriaRepository.findAll();
        assertThat(auditoriaList).hasSize(databaseSizeBeforeUpdate);
        Auditoria testAuditoria = auditoriaList.get(auditoriaList.size() - 1);
        assertThat(testAuditoria.getUsuarioCreacion()).isEqualTo(DEFAULT_USUARIO_CREACION);
        assertThat(testAuditoria.getFechaCreacion()).isEqualTo(DEFAULT_FECHA_CREACION);
        assertThat(testAuditoria.getUsuarioModificacion()).isEqualTo(UPDATED_USUARIO_MODIFICACION);
        assertThat(testAuditoria.getFechaModificacion()).isEqualTo(UPDATED_FECHA_MODIFICACION);
        assertThat(testAuditoria.getRequest()).isEqualTo(DEFAULT_REQUEST);
        assertThat(testAuditoria.getResponse()).isEqualTo(DEFAULT_RESPONSE);
    }

    @Test
    @Transactional
    void fullUpdateAuditoriaWithPatch() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        int databaseSizeBeforeUpdate = auditoriaRepository.findAll().size();

        // Update the auditoria using partial update
        Auditoria partialUpdatedAuditoria = new Auditoria();
        partialUpdatedAuditoria.setId(auditoria.getId());

        partialUpdatedAuditoria
            .usuarioCreacion(UPDATED_USUARIO_CREACION)
            .fechaCreacion(UPDATED_FECHA_CREACION)
            .usuarioModificacion(UPDATED_USUARIO_MODIFICACION)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION)
            .request(UPDATED_REQUEST)
            .response(UPDATED_RESPONSE);

        restAuditoriaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAuditoria.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAuditoria))
            )
            .andExpect(status().isOk());

        // Validate the Auditoria in the database
        List<Auditoria> auditoriaList = auditoriaRepository.findAll();
        assertThat(auditoriaList).hasSize(databaseSizeBeforeUpdate);
        Auditoria testAuditoria = auditoriaList.get(auditoriaList.size() - 1);
        assertThat(testAuditoria.getUsuarioCreacion()).isEqualTo(UPDATED_USUARIO_CREACION);
        assertThat(testAuditoria.getFechaCreacion()).isEqualTo(UPDATED_FECHA_CREACION);
        assertThat(testAuditoria.getUsuarioModificacion()).isEqualTo(UPDATED_USUARIO_MODIFICACION);
        assertThat(testAuditoria.getFechaModificacion()).isEqualTo(UPDATED_FECHA_MODIFICACION);
        assertThat(testAuditoria.getRequest()).isEqualTo(UPDATED_REQUEST);
        assertThat(testAuditoria.getResponse()).isEqualTo(UPDATED_RESPONSE);
    }

    @Test
    @Transactional
    void patchNonExistingAuditoria() throws Exception {
        int databaseSizeBeforeUpdate = auditoriaRepository.findAll().size();
        auditoria.setId(count.incrementAndGet());

        // Create the Auditoria
        AuditoriaDTO auditoriaDTO = auditoriaMapper.toDto(auditoria);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuditoriaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, auditoriaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(auditoriaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Auditoria in the database
        List<Auditoria> auditoriaList = auditoriaRepository.findAll();
        assertThat(auditoriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAuditoria() throws Exception {
        int databaseSizeBeforeUpdate = auditoriaRepository.findAll().size();
        auditoria.setId(count.incrementAndGet());

        // Create the Auditoria
        AuditoriaDTO auditoriaDTO = auditoriaMapper.toDto(auditoria);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuditoriaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(auditoriaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Auditoria in the database
        List<Auditoria> auditoriaList = auditoriaRepository.findAll();
        assertThat(auditoriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAuditoria() throws Exception {
        int databaseSizeBeforeUpdate = auditoriaRepository.findAll().size();
        auditoria.setId(count.incrementAndGet());

        // Create the Auditoria
        AuditoriaDTO auditoriaDTO = auditoriaMapper.toDto(auditoria);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuditoriaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(auditoriaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Auditoria in the database
        List<Auditoria> auditoriaList = auditoriaRepository.findAll();
        assertThat(auditoriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAuditoria() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        int databaseSizeBeforeDelete = auditoriaRepository.findAll().size();

        // Delete the auditoria
        restAuditoriaMockMvc
            .perform(delete(ENTITY_API_URL_ID, auditoria.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Auditoria> auditoriaList = auditoriaRepository.findAll();
        assertThat(auditoriaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
