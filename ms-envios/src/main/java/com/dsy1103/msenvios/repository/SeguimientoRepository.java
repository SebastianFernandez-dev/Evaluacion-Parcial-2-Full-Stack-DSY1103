package com.dsy1103.msenvios.repository;

import com.dsy1103.msenvios.modelo.SeguimientoModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeguimientoRepository extends JpaRepository<SeguimientoModelo, Long> {

    List<SeguimientoModelo> findByEnvio_Id(Long id);

    List<SeguimientoModelo> findByvisibleTrue();
}
