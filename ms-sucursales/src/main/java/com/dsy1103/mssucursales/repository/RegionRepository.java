package com.dsy1103.mssucursales.repository;

import com.dsy1103.mssucursales.model.RegionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<RegionModel, Long> {
}
