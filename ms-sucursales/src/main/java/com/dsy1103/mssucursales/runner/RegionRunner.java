package com.dsy1103.mssucursales.runner;

import com.dsy1103.mssucursales.model.RegionModel;
import com.dsy1103.mssucursales.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Order(1)
public class RegionRunner implements CommandLineRunner {

    @Autowired
    RegionRepository regionRepository;

    @Override
    public void run(String... args) throws Exception {
        if (!regionRepository.existsById(1L)) {
            regionRepository.save(RegionModel.builder()
                    .id(null)
                    .nombre("Metropolitana")
                    .codigo("RM-13")
                    .descripcion("Sede de la capital nacional y principal centro administrativo.")
                    .pais("Chile")
                    .fechaCreacion(LocalDate.of(1975, 7, 13))
                    .build());
        }

        if (!regionRepository.existsById(2L)) {
            regionRepository.save(RegionModel.builder()
                    .id(null)
                    .nombre("Biobío")
                    .codigo("REG-08")
                    .descripcion("Zona macrozona sur, fuerte actividad industrial y portuaria.")
                    .pais("Chile")
                    .fechaCreacion(LocalDate.of(1974, 10, 26))
                    .build());
        }

        if (!regionRepository.existsById(3L)) {
            regionRepository.save(RegionModel.builder()
                    .id(null)
                    .nombre("Valparaíso")
                    .codigo("REG-05")
                    .descripcion("Zona costera, principal puerto comercial y atractivo turístico.")
                    .pais("Chile")
                    .fechaCreacion(LocalDate.of(1974, 9, 29))
                    .build());
        }

        System.out.println("DATOS iniciales de REGION cargados CORRECTAMENTE");
    }
}
