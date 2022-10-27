package co.com.prueba.peigo.service.impl;

import co.com.prueba.peigo.domain.Auditoria;
import co.com.prueba.peigo.repository.AuditoriaRepository;
import co.com.prueba.peigo.service.AuditoriaService;
import co.com.prueba.peigo.service.dto.AuditoriaDTO;
import co.com.prueba.peigo.service.mapper.AuditoriaMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Auditoria}.
 */
@Service
@Transactional
public class AuditoriaServiceImpl implements AuditoriaService {

    private final Logger log = LoggerFactory.getLogger(AuditoriaServiceImpl.class);

    private final AuditoriaRepository auditoriaRepository;

    private final AuditoriaMapper auditoriaMapper;

    public AuditoriaServiceImpl(AuditoriaRepository auditoriaRepository, AuditoriaMapper auditoriaMapper) {
        this.auditoriaRepository = auditoriaRepository;
        this.auditoriaMapper = auditoriaMapper;
    }

    @Override
    public AuditoriaDTO save(AuditoriaDTO auditoriaDTO) {
        log.debug("Request to save Auditoria : {}", auditoriaDTO);
        Auditoria auditoria = auditoriaMapper.toEntity(auditoriaDTO);
        auditoria = auditoriaRepository.save(auditoria);
        return auditoriaMapper.toDto(auditoria);
    }

    @Override
    public AuditoriaDTO update(AuditoriaDTO auditoriaDTO) {
        log.debug("Request to save Auditoria : {}", auditoriaDTO);
        Auditoria auditoria = auditoriaMapper.toEntity(auditoriaDTO);
        auditoria = auditoriaRepository.save(auditoria);
        return auditoriaMapper.toDto(auditoria);
    }

    @Override
    public Optional<AuditoriaDTO> partialUpdate(AuditoriaDTO auditoriaDTO) {
        log.debug("Request to partially update Auditoria : {}", auditoriaDTO);

        return auditoriaRepository
            .findById(auditoriaDTO.getId())
            .map(existingAuditoria -> {
                auditoriaMapper.partialUpdate(existingAuditoria, auditoriaDTO);

                return existingAuditoria;
            })
            .map(auditoriaRepository::save)
            .map(auditoriaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuditoriaDTO> findAll() {
        log.debug("Request to get all Auditorias");
        return auditoriaRepository.findAll().stream().map(auditoriaMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AuditoriaDTO> findOne(Long id) {
        log.debug("Request to get Auditoria : {}", id);
        return auditoriaRepository.findById(id).map(auditoriaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Auditoria : {}", id);
        auditoriaRepository.deleteById(id);
    }
}
