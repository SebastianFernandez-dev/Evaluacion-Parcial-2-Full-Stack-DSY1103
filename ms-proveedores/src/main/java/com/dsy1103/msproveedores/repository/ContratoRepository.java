package com.dsy1103.msproveedores.repository;

import com.dsy1103.msproveedores.model.ContratoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContratoRepository extends JpaRepository<ContratoModel, Long> {
}
