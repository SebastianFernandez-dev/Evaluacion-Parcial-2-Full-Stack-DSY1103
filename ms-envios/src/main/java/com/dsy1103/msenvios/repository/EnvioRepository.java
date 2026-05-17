package com.dsy1103.msenvios.repository;

import com.dsy1103.msenvios.modelo.EnvioModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.time.LocalDateTime;
import org.springframework.data.repository.query.Param;
import java.util.List;

@Repository
public interface EnvioRepository extends JpaRepository<EnvioModelo,Long> {

    Optional<EnvioModelo> findBycodigoEnvio(String codigoEnvio);

    @Query("SELECT e FROM EnvioModelo e WHERE e.fechaSalida BETWEEN :inicio AND :fin AND e.estadoEnvio <> 'Entregado'")
    List<EnvioModelo> findEnviosEnRangoNoEntregados(
            @Param("inicio") LocalDateTime inicio,
            @Param("fin") LocalDateTime fin
    );

}
