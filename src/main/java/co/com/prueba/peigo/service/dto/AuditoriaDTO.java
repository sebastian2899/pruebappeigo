package co.com.prueba.peigo.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link co.com.prueba.peigo.domain.Auditoria} entity.
 */
public class AuditoriaDTO implements Serializable {

    private Long id;

    private Long usuarioCreacion;

    private Instant fechaCreacion;

    private Long usuarioModificacion;

    private Instant fechaModificacion;

    private String request;

    private String response;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(Long usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public Instant getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Instant fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Long getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(Long usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public Instant getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Instant fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AuditoriaDTO)) {
            return false;
        }

        AuditoriaDTO auditoriaDTO = (AuditoriaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, auditoriaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AuditoriaDTO{" +
            "id=" + getId() +
            ", usuarioCreacion=" + getUsuarioCreacion() +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            ", usuarioModificacion=" + getUsuarioModificacion() +
            ", fechaModificacion='" + getFechaModificacion() + "'" +
            ", request='" + getRequest() + "'" +
            ", response='" + getResponse() + "'" +
            "}";
    }
}
