package com.dsy1103.msproveedores.runner;

import com.dsy1103.msproveedores.model.ProveedorModel;
import com.dsy1103.msproveedores.repository.ProveedorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Order(1)
@Slf4j
public class ProveedorRunner implements CommandLineRunner {

    @Autowired
    ProveedorRepository proveedorRepository;

    @Override
    public void run(String... args) throws Exception {

        if (!proveedorRepository.existsById(1L)) {
            proveedorRepository.save(ProveedorModel.builder()
                            .id(null)
                            .nombre("Alfa Distribuciones")
                            .razonSocial("Distribuidora e Importaciones Alfa S.A.")
                            .documentoFiscal("76.123.456-7")
                            .correoContacto("contacto@alfadistribuciones.cl")
                            .ciudad("Santiago Centro")
                            .calificacion(5)
                            .activo(true)
                            .fechaRegistro(LocalDate.of(2025, 3, 12))
                            .build());
        }

        if (!proveedorRepository.existsById(2L)) {
            proveedorRepository.save(ProveedorModel.builder()
                    .nombre("Inmobiliaria del Sur")
                    .razonSocial("Constructora e Inmobiliaria del Sur Ltda.")
                    .documentoFiscal("88.777.666-k")
                    .correoContacto("operaciones@indelsur.cl")
                    .ciudad("Concepción")
                    .calificacion(4)
                    .activo(true)
                    .fechaRegistro(LocalDate.of(2025, 8, 20))
                    .build());
        }

        if (!proveedorRepository.existsById(3L)) {
            proveedorRepository.save(ProveedorModel.builder()
                    .nombre("Beta Tecnologías")
                    .razonSocial("Servicios Tecnológicos Beta SpA")
                    .documentoFiscal("77.999.888-5")
                    .correoContacto("soporte@betatech.cl")
                    .ciudad("Valparaíso")
                    .calificacion(3)
                    .activo(false)
                    .fechaRegistro(LocalDate.of(2026, 1, 15))
                    .build());
        }

        log.info("DATOS iniciales de PROVEEDOR cargados CORRECTAMENTE");
    }
}
