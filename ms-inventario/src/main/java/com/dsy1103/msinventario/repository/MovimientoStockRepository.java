package com.dsy1103.msinventario.repository;

import com.dsy1103.msinventario.model.MovimientoStockModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimientoStockRepository extends JpaRepository<MovimientoStockModel, Long> {

    List<MovimientoStockModel> findByInventarioId(Long inventarioId);
}
