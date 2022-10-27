package co.com.prueba.peigo.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AuditoriaMapperTest {

    private AuditoriaMapper auditoriaMapper;

    @BeforeEach
    public void setUp() {
        auditoriaMapper = new AuditoriaMapperImpl();
    }
}
