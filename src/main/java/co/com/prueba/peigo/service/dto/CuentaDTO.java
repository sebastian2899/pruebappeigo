package co.com.prueba.peigo.service.dto;

import java.io.Serializable;
import java.util.Objects;

import co.com.prueba.peigo.domain.enumeration.TipoCuenta;

/**
 * A DTO for the {@link co.com.prueba.peigo.domain.Cuenta} entity.
 */
public class CuentaDTO extends AuditoriaDTO implements Serializable {

    private Long id;

    private String numeroCuenta;

    private Double saldo;

    private TipoCuenta tipoCuenta;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(TipoCuenta tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CuentaDTO)) {
            return false;
        }

        CuentaDTO cuentaDTO = (CuentaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cuentaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CuentaDTO{" +
            "id=" + getId() +
            ", numeroCuenta='" + getNumeroCuenta() + "'" +
            ", saldo=" + getSaldo() +
            ", tipoCuenta='" + getTipoCuenta() + "'" +
            "}";
    }
}
