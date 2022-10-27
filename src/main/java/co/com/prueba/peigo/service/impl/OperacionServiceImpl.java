package co.com.prueba.peigo.service.impl;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import co.com.prueba.peigo.domain.Operacion;
import co.com.prueba.peigo.repository.OperacionRepository;
import co.com.prueba.peigo.service.OperacionService;
import co.com.prueba.peigo.service.TrazabilidadService;
import co.com.prueba.peigo.service.dto.OperacionDTO;
import co.com.prueba.peigo.service.dto.TrazabilidadDTO;
import co.com.prueba.peigo.service.mapper.OperacionMapper;

/**
 * Service Implementation for managing {@link Operacion}.
 */
@Service
@Transactional
public class OperacionServiceImpl implements OperacionService {

    private final Logger log = LoggerFactory.getLogger(OperacionServiceImpl.class);

    private final OperacionRepository operacionRepository;

    private final OperacionMapper operacionMapper;
    
    private final TrazabilidadService trazabilidadService;
    
    Gson gson = new Gson();
    
    public OperacionServiceImpl(OperacionRepository operacionRepository, OperacionMapper operacionMapper, TrazabilidadService trazabilidadService) {
        this.operacionRepository = operacionRepository;
        this.operacionMapper = operacionMapper;
		this.trazabilidadService = trazabilidadService;
    }
    
    private TrazabilidadDTO crearRegistroAuditoria(OperacionDTO cuentaDTO, Operacion cuenta) {
    	TrazabilidadDTO dto = new TrazabilidadDTO();
    	dto.setRequest(gson.toJson(cuentaDTO));
    	dto.setResponse(gson.toJson(cuenta));
    	dto.setProceso(cuentaDTO.getId() != null ? "ACTUALIZACION OPERACION" : "CREACION OPERACION");
    	dto.setUsuarioCreacion(cuentaDTO.getId() != null ? cuentaDTO.getUsuarioModificacion() : cuentaDTO.getUsuarioModificacion());
		return dto;
	}

    @Override
    public OperacionDTO save(OperacionDTO operacionDTO) {
        log.debug("Request to save Operacion : {}", operacionDTO);
        Operacion operacion = operacionMapper.toEntity(operacionDTO);
        operacion.setFechaCreacion(Instant.now());
        operacion = operacionRepository.save(operacion);
        //se registra trazabilidad del proceso
        trazabilidadService.save(crearRegistroAuditoria(operacionDTO, operacion));
        return operacionMapper.toDto(operacion);
    }

    @Override
    public OperacionDTO update(OperacionDTO operacionDTO) {
        log.debug("Request to save Operacion : {}", operacionDTO);
        Operacion operacion = operacionMapper.toEntity(operacionDTO);
        operacion.setFechaModificacion(Instant.now());
        operacion = operacionRepository.save(operacion);
        //se registra trazabilidad del proceso
        trazabilidadService.save(crearRegistroAuditoria(operacionDTO, operacion));
        return operacionMapper.toDto(operacion);
    }

    @Override
    public Optional<OperacionDTO> partialUpdate(OperacionDTO operacionDTO) {
        log.debug("Request to partially update Operacion : {}", operacionDTO);

        return operacionRepository
            .findById(operacionDTO.getId())
            .map(existingOperacion -> {
                operacionMapper.partialUpdate(existingOperacion, operacionDTO);

                return existingOperacion;
            })
            .map(operacionRepository::save)
            .map(operacionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OperacionDTO> findAll() {
        log.debug("Request to get all Operacions");
        return operacionRepository.findAll().stream().map(operacionMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OperacionDTO> findOne(Long id) {
        log.debug("Request to get Operacion : {}", id);
        return operacionRepository.findById(id).map(operacionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Operacion : {}", id);
        operacionRepository.deleteById(id);
    }
}
