package co.com.prueba.peigo.service.impl;

import co.com.prueba.peigo.domain.Operacion;
import co.com.prueba.peigo.repository.OperacionRepository;
import co.com.prueba.peigo.service.OperacionService;
import co.com.prueba.peigo.service.dto.OperacionDTO;
import co.com.prueba.peigo.service.mapper.OperacionMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Operacion}.
 */
@Service
@Transactional
public class OperacionServiceImpl implements OperacionService {

    private final Logger log = LoggerFactory.getLogger(OperacionServiceImpl.class);

    private final OperacionRepository operacionRepository;

    private final OperacionMapper operacionMapper;

    public OperacionServiceImpl(OperacionRepository operacionRepository, OperacionMapper operacionMapper) {
        this.operacionRepository = operacionRepository;
        this.operacionMapper = operacionMapper;
    }

    @Override
    public OperacionDTO save(OperacionDTO operacionDTO) {
        log.debug("Request to save Operacion : {}", operacionDTO);
        Operacion operacion = operacionMapper.toEntity(operacionDTO);
        operacion = operacionRepository.save(operacion);
        return operacionMapper.toDto(operacion);
    }

    @Override
    public OperacionDTO update(OperacionDTO operacionDTO) {
        log.debug("Request to save Operacion : {}", operacionDTO);
        Operacion operacion = operacionMapper.toEntity(operacionDTO);
        operacion = operacionRepository.save(operacion);
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
