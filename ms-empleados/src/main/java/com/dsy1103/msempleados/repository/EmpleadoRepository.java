package com.dsy1103.msempleados.repository;

import com.dsy1103.msempleados.model.EmpleadoModel;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmpleadoRepository extends JpaRepository<EmpleadoModel, Long> {

    @Query(value ="SELECT * FROM empleado e WHERE e.sucursal_id = :sucursalId " +
            "AND YEAR(e.fecha_ingreso) = :anio",nativeQuery = true)
    List<EmpleadoModel> findAllBySucursalAndAnio(@Param("sucursalId") Long sucursalId,
                                                 @Param("anio") int anio);
}
