package com.dsy1103.msusuarios.repository;

import com.dsy1103.msusuarios.modelo.UsuarioModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModelo, Long> {


    List<UsuarioModelo> findByCorreoUsuarioAndActivoTrue(String correo);

    Optional<UsuarioModelo> findByCorreoUsuario(String correoUsuario);

    List<UsuarioModelo> findByActivoTrue();

    Optional<UsuarioModelo> findByRut(Integer rut);

    List<UsuarioModelo> findByPrimerNombreContainingIgnoreCase(String nombre);


}