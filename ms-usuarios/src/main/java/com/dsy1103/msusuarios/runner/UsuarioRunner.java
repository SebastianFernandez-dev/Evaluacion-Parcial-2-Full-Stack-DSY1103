package com.dsy1103.msusuarios.runner;

import com.dsy1103.msusuarios.modelo.PerfilModelo;
import com.dsy1103.msusuarios.modelo.UsuarioModelo;
import com.dsy1103.msusuarios.repository.PerfilRepository;
import com.dsy1103.msusuarios.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Order(1)
@Slf4j
public class UsuarioRunner implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private PerfilRepository perfilRepo;

    @Override
    public void run(String... args) throws Exception {
        if (!usuarioRepo.existsById(1L)) {
            log.info("Poblando datos de prueba para ms-usuarios...");

            UsuarioModelo u1 = new UsuarioModelo();
            u1.setPrimerNombre("Milton");
            u1.setSegundoNombre("Andres");
            u1.setPrimerApellido("Tenazo");
            u1.setSegundoApellido("Panduro");
            u1.setCorreoUsuario("milton@correo.cl");
            u1.setRut(11222333);
            u1.setDvRut(1);
            u1.setFechaRegistro(LocalDate.now());
            u1.setActivo(true);
            usuarioRepo.save(u1);

            PerfilModelo p1 = new PerfilModelo();
            p1.setNombrePerfil("ADMIN");
            p1.setDescripcion("Acceso total");
            p1.setNivelAcessoPerfil(10);
            p1.setActivo(true);
            p1.setFechaCreacionPerfil(LocalDate.now());
            p1.setUsuario(u1);
            perfilRepo.save(p1);

            UsuarioModelo u2 = new UsuarioModelo();
            u2.setPrimerNombre("Juan");
            u2.setSegundoNombre("Pablo");
            u2.setPrimerApellido("Perez");
            u2.setSegundoApellido("Soto");
            u2.setCorreoUsuario("juan.perez@correo.cl");
            u2.setRut(22333444);
            u2.setDvRut(5);
            u2.setFechaRegistro(LocalDate.now());
            u2.setActivo(true);
            usuarioRepo.save(u2);

            UsuarioModelo u3 = new UsuarioModelo();
            u3.setPrimerNombre("Maria");
            u3.setSegundoNombre("Jose");
            u3.setPrimerApellido("Lorca");
            u3.setSegundoApellido("Ruiz");
            u3.setCorreoUsuario("m.lorca@correo.cl");
            u3.setRut(33444555);
            u3.setDvRut(0);
            u3.setFechaRegistro(LocalDate.now());
            u3.setActivo(false);
            usuarioRepo.save(u3);

            log.info("¡Datos cargados con éxito! 3 usuarios creados.");
        }
    }
}