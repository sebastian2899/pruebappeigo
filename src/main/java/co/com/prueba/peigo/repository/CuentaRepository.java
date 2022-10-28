package co.com.prueba.peigo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.com.prueba.peigo.domain.Cuenta;

/**
 * Spring Data SQL repository for the Cuenta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

	@Query("SELECT c From Cuenta c Where c.numeroCuenta=:numeroCuenta")
	Optional<Cuenta> findByNumber(@Param("numeroCuenta") String numeroCuenta);
	}
