package com.dsy1103.msempleados.repository;

import com.dsy1103.msempleados.model.EmpleadoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<EmpleadoModel, Long> {
}
