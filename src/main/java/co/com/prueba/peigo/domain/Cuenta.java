package co.com.prueba.peigo.domain;

import co.com.prueba.peigo.domain.enumeration.TipoCuenta;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Cuenta.
 */
@Entity
@Table(name = "cuenta")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Cuenta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "numero_cuenta")
    private String numeroCuenta;

    @Column(name = "saldo")
    private Double saldo;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cuenta")
    private TipoCuenta tipoCuenta;

    @OneToOne
    @JoinColumn(unique = true)
    private Auditoria auditoria;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Cuenta id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroCuenta() {
        return this.numeroCuenta;
    }

    public Cuenta numeroCuenta(String numeroCuenta) {
        this.setNumeroCuenta(numeroCuenta);
        return this;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public Double getSaldo() {
        return this.saldo;
    }

    public Cuenta saldo(Double saldo) {
        this.setSaldo(saldo);
        return this;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public TipoCuenta getTipoCuenta() {
        return this.tipoCuenta;
    }

    public Cuenta tipoCuenta(TipoCuenta tipoCuenta) {
        this.setTipoCuenta(tipoCuenta);
        return this;
    }

    public void setTipoCuenta(TipoCuenta tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public Auditoria getAuditoria() {
        return this.auditoria;
    }

    public void setAuditoria(Auditoria auditoria) {
        this.auditoria = auditoria;
    }

    public Cuenta auditoria(Auditoria auditoria) {
        this.setAuditoria(auditoria);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cuenta)) {
            return false;
        }
        return id != null && id.equals(((Cuenta) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cuenta{" +
            "id=" + getId() +
            ", numeroCuenta='" + getNumeroCuenta() + "'" +
            ", saldo=" + getSaldo() +
            ", tipoCuenta='" + getTipoCuenta() + "'" +
            "}";
    }
}
