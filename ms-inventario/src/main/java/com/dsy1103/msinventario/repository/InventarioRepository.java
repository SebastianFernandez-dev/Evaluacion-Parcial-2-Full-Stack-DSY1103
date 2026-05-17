package com.dsy1103.msinventario.repository;

import com.dsy1103.msinventario.model.InventarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventarioRepository extends JpaRepository<InventarioModel, Long> {

    List<InventarioModel> findByCantidadDisponibleGreaterThanAndActivoTrue(Integer cantidadDisponible);
}
