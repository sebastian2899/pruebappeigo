package co.com.prueba.peigo.service.impl;

import co.com.prueba.peigo.domain.Cuenta;
import co.com.prueba.peigo.repository.CuentaRepository;
import co.com.prueba.peigo.service.CuentaService;
import co.com.prueba.peigo.service.dto.CuentaDTO;
import co.com.prueba.peigo.service.mapper.CuentaMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Cuenta}.
 */
@Service
@Transactional
public class CuentaServiceImpl implements CuentaService {

    private final Logger log = LoggerFactory.getLogger(CuentaServiceImpl.class);

    private final CuentaRepository cuentaRepository;

    private final CuentaMapper cuentaMapper;

    public CuentaServiceImpl(CuentaRepository cuentaRepository, CuentaMapper cuentaMapper) {
        this.cuentaRepository = cuentaRepository;
        this.cuentaMapper = cuentaMapper;
    }

    @Override
    public CuentaDTO save(CuentaDTO cuentaDTO) {
        log.debug("Request to save Cuenta : {}", cuentaDTO);
        Cuenta cuenta = cuentaMapper.toEntity(cuentaDTO);
        cuenta = cuentaRepository.save(cuenta);
        return cuentaMapper.toDto(cuenta);
    }

    @Override
    public CuentaDTO update(CuentaDTO cuentaDTO) {
        log.debug("Request to save Cuenta : {}", cuentaDTO);
        Cuenta cuenta = cuentaMapper.toEntity(cuentaDTO);
        cuenta = cuentaRepository.save(cuenta);
        return cuentaMapper.toDto(cuenta);
    }

    @Override
    public Optional<CuentaDTO> partialUpdate(CuentaDTO cuentaDTO) {
        log.debug("Request to partially update Cuenta : {}", cuentaDTO);

        return cuentaRepository
            .findById(cuentaDTO.getId())
            .map(existingCuenta -> {
                cuentaMapper.partialUpdate(existingCuenta, cuentaDTO);

                return existingCuenta;
            })
            .map(cuentaRepository::save)
            .map(cuentaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CuentaDTO> findAll() {
        log.debug("Request to get all Cuentas");
        return cuentaRepository.findAll().stream().map(cuentaMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CuentaDTO> findOne(Long id) {
        log.debug("Request to get Cuenta : {}", id);
        return cuentaRepository.findById(id).map(cuentaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cuenta : {}", id);
        cuentaRepository.deleteById(id);
    }
}
