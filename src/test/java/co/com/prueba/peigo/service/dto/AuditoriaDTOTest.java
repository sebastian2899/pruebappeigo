package co.com.prueba.peigo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.com.prueba.peigo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AuditoriaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuditoriaDTO.class);
        AuditoriaDTO auditoriaDTO1 = new AuditoriaDTO();
        auditoriaDTO1.setId(1L);
        AuditoriaDTO auditoriaDTO2 = new AuditoriaDTO();
        assertThat(auditoriaDTO1).isNotEqualTo(auditoriaDTO2);
        auditoriaDTO2.setId(auditoriaDTO1.getId());
        assertThat(auditoriaDTO1).isEqualTo(auditoriaDTO2);
        auditoriaDTO2.setId(2L);
        assertThat(auditoriaDTO1).isNotEqualTo(auditoriaDTO2);
        auditoriaDTO1.setId(null);
        assertThat(auditoriaDTO1).isNotEqualTo(auditoriaDTO2);
    }
}
