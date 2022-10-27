package co.com.prueba.peigo.service.mapper;

import co.com.prueba.peigo.domain.Auditoria;
import co.com.prueba.peigo.domain.Operacion;
import co.com.prueba.peigo.service.dto.AuditoriaDTO;
import co.com.prueba.peigo.service.dto.OperacionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Operacion} and its DTO {@link OperacionDTO}.
 */
@Mapper(componentModel = "spring")
public interface OperacionMapper extends EntityMapper<OperacionDTO, Operacion> {
    @Mapping(target = "auditoria", source = "auditoria", qualifiedByName = "auditoriaId")
    OperacionDTO toDto(Operacion s);

    @Named("auditoriaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AuditoriaDTO toDtoAuditoriaId(Auditoria auditoria);
}
