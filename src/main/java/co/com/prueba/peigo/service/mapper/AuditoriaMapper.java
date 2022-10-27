package co.com.prueba.peigo.service.mapper;

import co.com.prueba.peigo.domain.Auditoria;
import co.com.prueba.peigo.service.dto.AuditoriaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Auditoria} and its DTO {@link AuditoriaDTO}.
 */
@Mapper(componentModel = "spring")
public interface AuditoriaMapper extends EntityMapper<AuditoriaDTO, Auditoria> {}
