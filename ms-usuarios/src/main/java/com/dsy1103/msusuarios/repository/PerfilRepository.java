package com.dsy1103.msusuarios.repository;

import com.dsy1103.msusuarios.modelo.PerfilModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PerfilRepository extends JpaRepository<PerfilModelo, Long> {

    // Metodo para buscar perfiles que pertenezcan a un usuario
    List<PerfilModelo> findByUsuario_Id(Long usuarioId);

    // Metodo para buscar perfiles activos
    List<PerfilModelo> findByActivoTrue();
}