package co.com.prueba.peigo.service.mapper;

import co.com.prueba.peigo.domain.Auditoria;
import co.com.prueba.peigo.domain.Cuenta;
import co.com.prueba.peigo.service.dto.AuditoriaDTO;
import co.com.prueba.peigo.service.dto.CuentaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cuenta} and its DTO {@link CuentaDTO}.
 */
@Mapper(componentModel = "spring")
public interface CuentaMapper extends EntityMapper<CuentaDTO, Cuenta> {
    @Mapping(target = "auditoria", source = "auditoria", qualifiedByName = "auditoriaId")
    CuentaDTO toDto(Cuenta s);

    @Named("auditoriaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AuditoriaDTO toDtoAuditoriaId(Auditoria auditoria);
}
