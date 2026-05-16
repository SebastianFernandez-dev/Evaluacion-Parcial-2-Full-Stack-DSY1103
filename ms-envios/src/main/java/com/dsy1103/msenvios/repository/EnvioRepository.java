package com.dsy1103.msenvios.repository;

import com.dsy1103.msenvios.modelo.EnvioModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnvioRepository extends JpaRepository<EnvioModelo,Long> {

    Optional<EnvioModelo> findBycodigoEnvio(String codigoEnvio);

}
