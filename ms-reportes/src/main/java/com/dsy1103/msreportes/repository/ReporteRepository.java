package com.dsy1103.msreportes.repository;

import com.dsy1103.msreportes.model.ReporteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReporteRepository extends JpaRepository<ReporteModel, Long> {

    List<ReporteModel> findByUsuarioId(Long id);
}
