package com.dsy1103.mssucursales.repository;

import com.dsy1103.mssucursales.model.SucursalModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SucursalRepository extends JpaRepository<SucursalModel, Long> {

    @Query(value = "SELECT s.* FROM sucursal s JOIN region r " +
            "ON (r.id = s.fk_region_id) WHERE r.nombre = :nombre", nativeQuery=true)
    List<SucursalModel> findAllByRegionNombre(@Param("nombre") String nombre);
}
