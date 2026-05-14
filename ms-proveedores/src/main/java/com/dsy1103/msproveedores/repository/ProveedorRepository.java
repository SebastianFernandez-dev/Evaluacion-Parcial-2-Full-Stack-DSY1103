package com.dsy1103.msproveedores.repository;

import com.dsy1103.msproveedores.model.ProveedorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProveedorRepository extends JpaRepository<ProveedorModel, Long> {

    @Query(value = "SELECT * FROM proveedor WHERE activo = 1 ORDER BY ASC nombre", nativeQuery=true)
    List<ProveedorModel> findAllByActivo();
}
