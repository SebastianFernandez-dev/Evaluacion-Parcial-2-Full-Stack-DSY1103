package com.dsy1103.mspagos.repository;

import com.dsy1103.mspagos.model.PagoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PagoRepository extends JpaRepository<PagoModel, Long> {

    @Query(value = "SELECT * FROM pagos p WHERE p.monto > :monto " +
            "AND p.estado_pago = :estadoPago", nativeQuery = true)
    List<PagoModel> findByMontoGreaterThanAndEstadoPago(@Param("monto") Double monto, @Param("estadoPago") String estadoPago);
}
