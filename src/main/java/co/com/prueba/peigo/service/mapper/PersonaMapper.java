package co.com.prueba.peigo.service.mapper;

import co.com.prueba.peigo.domain.Auditoria;
import co.com.prueba.peigo.domain.Cuenta;
import co.com.prueba.peigo.domain.Persona;
import co.com.prueba.peigo.service.dto.AuditoriaDTO;
import co.com.prueba.peigo.service.dto.CuentaDTO;
import co.com.prueba.peigo.service.dto.PersonaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Persona} and its DTO {@link PersonaDTO}.
 */
@Mapper(componentModel = "spring")
public interface PersonaMapper extends EntityMapper<PersonaDTO, Persona> {
    @Mapping(target = "cuenta", source = "cuenta", qualifiedByName = "cuentaId")
    @Mapping(target = "auditoria", source = "auditoria", qualifiedByName = "auditoriaId")
    PersonaDTO toDto(Persona s);

    @Named("cuentaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CuentaDTO toDtoCuentaId(Cuenta cuenta);

    @Named("auditoriaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AuditoriaDTO toDtoAuditoriaId(Auditoria auditoria);
}
