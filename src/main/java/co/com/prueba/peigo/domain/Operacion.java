package co.com.prueba.peigo.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Operacion.
 */
@Entity
@Table(name = "operacion")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Operacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "numero_operacion")
    private String numeroOperacion;

    @Column(name = "monto")
    private Double monto;

    @Column(name = "cuenta_origen")
    private Long cuentaOrigen;

    @Column(name = "cuenta_destino")
    private Long cuentaDestino;

    @OneToOne
    @JoinColumn(unique = true)
    private Auditoria auditoria;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Operacion id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroOperacion() {
        return this.numeroOperacion;
    }

    public Operacion numeroOperacion(String numeroOperacion) {
        this.setNumeroOperacion(numeroOperacion);
        return this;
    }

    public void setNumeroOperacion(String numeroOperacion) {
        this.numeroOperacion = numeroOperacion;
    }

    public Double getMonto() {
        return this.monto;
    }

    public Operacion monto(Double monto) {
        this.setMonto(monto);
        return this;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Long getCuentaOrigen() {
        return this.cuentaOrigen;
    }

    public Operacion cuentaOrigen(Long cuentaOrigen) {
        this.setCuentaOrigen(cuentaOrigen);
        return this;
    }

    public void setCuentaOrigen(Long cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public Long getCuentaDestino() {
        return this.cuentaDestino;
    }

    public Operacion cuentaDestino(Long cuentaDestino) {
        this.setCuentaDestino(cuentaDestino);
        return this;
    }

    public void setCuentaDestino(Long cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

    public Auditoria getAuditoria() {
        return this.auditoria;
    }

    public void setAuditoria(Auditoria auditoria) {
        this.auditoria = auditoria;
    }

    public Operacion auditoria(Auditoria auditoria) {
        this.setAuditoria(auditoria);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Operacion)) {
            return false;
        }
        return id != null && id.equals(((Operacion) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Operacion{" +
            "id=" + getId() +
            ", numeroOperacion='" + getNumeroOperacion() + "'" +
            ", monto=" + getMonto() +
            ", cuentaOrigen=" + getCuentaOrigen() +
            ", cuentaDestino=" + getCuentaDestino() +
            "}";
    }
}
