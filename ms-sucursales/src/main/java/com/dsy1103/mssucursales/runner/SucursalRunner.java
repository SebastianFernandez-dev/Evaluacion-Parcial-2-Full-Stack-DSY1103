package com.dsy1103.mssucursales.runner;

import com.dsy1103.mssucursales.model.SucursalModel;
import com.dsy1103.mssucursales.repository.RegionRepository;
import com.dsy1103.mssucursales.repository.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Order(2)
public class SucursalRunner implements CommandLineRunner {
    
    @Autowired
    SucursalRepository sucursalRepository;
    @Autowired
    RegionRepository regionRepository;

    @Override
    public void run(String... args) throws Exception {
        if (!sucursalRepository.existsById(1L)) {
            sucursalRepository.save(SucursalModel.builder()
                    .id(null)
                    .nombre("Sucursal Santiago Centro")
                    .codigo("SUC-RM-001")
                    .direccion("Av. Libertador Bernardo O'Higgins 1050")
                    .capacidadAtencion(150)
                    .activo(true)
                    .fechaApertura(LocalDate.of(2018, 5, 20))
                    .region(regionRepository.findById(1L).orElse(null))
                    .build());
        }

        if (!sucursalRepository.existsById(2L)) {
            sucursalRepository.save(SucursalModel.builder()
                    .id(null)
                    .nombre("Sucursal Concepción Portada")
                    .codigo("SUC-BB-002")
                    .direccion("Calle O'Higgins 450, Concepción")
                    .capacidadAtencion(90)
                    .activo(true)
                    .fechaApertura(LocalDate.of(2021, 11, 14))
                    .region(regionRepository.findById(2L).orElse(null))
                    .build());
        }

        if (!sucursalRepository.existsById(3L)) {
            sucursalRepository.save(SucursalModel.builder()
                    .id(null)
                    .nombre("Sucursal Providencia Hub")
                    .codigo("SUC-RM-003")
                    .direccion("Av. Providencia 2340")
                    .capacidadAtencion(120)
                    .activo(false)
                    .fechaApertura(LocalDate.of(2024, 2, 10))
                    .region(regionRepository.findById(1L).orElse(null))
                    .build());
        }

        System.out.println("DATOS iniciales de SUCURSALES cargados CORRECTAMENTE");
    }
}
