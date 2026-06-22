package com.dsy1103.msproductos.runner;

import com.dsy1103.msproductos.model.CategoriaModel;
import com.dsy1103.msproductos.repository.CategoriaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Order(1)
@Slf4j
public class CategoriaRunner implements CommandLineRunner {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public void run(String... args) throws Exception {
        if (!categoriaRepository.existsById(1L)) {
            categoriaRepository.save(CategoriaModel.builder()
                    .id(null)
                    .nombreCategoria("Electrónica")
                    .descripcion("Dispositivos y equipos electrónicos")
                    .codigoCategoria("ELEC-001")
                    .activoCategoria(true)
                    .fechaCreacion(LocalDate.of(2024, 1, 10))
                    .build());
        }
        if (!categoriaRepository.existsById(2L)) {
            categoriaRepository.save(CategoriaModel.builder()
                    .id(null)
                    .nombreCategoria("Hogar y Cocina")
                    .descripcion("Artículos para el hogar y cocina")
                    .codigoCategoria("HOG-002")
                    .activoCategoria(true)
                    .fechaCreacion(LocalDate.of(2024, 2, 15))
                    .build());
        }
        if (!categoriaRepository.existsById(3L)) {
            categoriaRepository.save(CategoriaModel.builder()
                    .id(null)
                    .nombreCategoria("Deportes")
                    .descripcion("Equipamiento y ropa deportiva")
                    .codigoCategoria("DEP-003")
                    .activoCategoria(true)
                    .fechaCreacion(LocalDate.of(2024, 3, 20))
                    .build());
        }
        System.out.println("DATOS iniciales de CATEGORIA cargados CORRECTAMENTE");
    }
}
