package com.dsy1103.msproveedores.repository;

import com.dsy1103.msproveedores.model.ContratoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContratoRepository extends JpaRepository<ContratoModel, Long> {

    List<ContratoModel> findByProveedorId(Long proveedorId);
}
