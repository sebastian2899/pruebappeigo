package co.com.prueba.peigo.service.dto;

import java.io.Serializable;
import java.time.Instant;

import lombok.Data;

/**
 * A DTO for the {@link co.com.prueba.peigo.domain.Operacion} entity.
 */
@Data
public class OperacionDTO implements Serializable {

    private Long id;

    private String numeroOperacion;

    private Double monto;

    private Long cuentaOrigen;

    private Long cuentaDestino;
    
    private Long usuarioCreacion;

    private Instant fechaCreacion;

    private Long usuarioModificacion;

    private Instant fechaModificacion;
   
}
