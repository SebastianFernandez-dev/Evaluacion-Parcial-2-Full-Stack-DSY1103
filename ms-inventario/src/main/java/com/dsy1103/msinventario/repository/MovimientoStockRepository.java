package com.dsy1103.msinventario.repository;

import com.dsy1103.msinventario.model.MovimientoStockModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimientoStockRepository extends JpaRepository<MovimientoStockModel, Long> {

    List<MovimientoStockModel> findByInventarioId(Long inventarioId);
}
