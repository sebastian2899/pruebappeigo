package co.com.prueba.peigo.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link co.com.prueba.peigo.domain.Operacion} entity.
 */
public class OperacionDTO extends AuditoriaDTO implements Serializable {

    private Long id;

    private String numeroOperacion;

    private Double monto;

    private Long cuentaOrigen;

    private Long cuentaDestino;

   
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroOperacion() {
        return numeroOperacion;
    }

    public void setNumeroOperacion(String numeroOperacion) {
        this.numeroOperacion = numeroOperacion;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Long getCuentaOrigen() {
        return cuentaOrigen;
    }

    public void setCuentaOrigen(Long cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public Long getCuentaDestino() {
        return cuentaDestino;
    }

    public void setCuentaDestino(Long cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OperacionDTO)) {
            return false;
        }

        OperacionDTO operacionDTO = (OperacionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, operacionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OperacionDTO{" +
            "id=" + getId() +
            ", numeroOperacion='" + getNumeroOperacion() + "'" +
            ", monto=" + getMonto() +
            ", cuentaOrigen=" + getCuentaOrigen() +
            ", cuentaDestino=" + getCuentaDestino() +
            "}";
    }
}
