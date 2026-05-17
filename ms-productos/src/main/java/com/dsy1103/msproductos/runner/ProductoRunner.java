package com.dsy1103.msproductos.runner;

import com.dsy1103.msproductos.model.ProductoModel;
import com.dsy1103.msproductos.repository.CategoriaRepository;
import com.dsy1103.msproductos.repository.ProductoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Order(2)
@Slf4j
public class ProductoRunner implements CommandLineRunner {

    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public void run(String... args) throws Exception {
        if (!productoRepository.existsById(1L)) {
            productoRepository.save(ProductoModel.builder()
                    .id(null)
                    .nombreProducto("Notebook Lenovo IdeaPad")
                    .descripcion("Notebook 15.6 pulgadas, 16GB RAM, 512GB SSD")
                    .sku("SKU-NB-001")
                    .precio(599990.0)
                    .activoProducto(true)
                    .fechaIngreso(LocalDate.of(2024, 4, 1))
                    .categoria(categoriaRepository.findById(1L).orElse(null))
                    .build());
        }
        if (!productoRepository.existsById(2L)) {
            productoRepository.save(ProductoModel.builder()
                    .id(null)
                    .nombreProducto("Smartphone Samsung Galaxy")
                    .descripcion("Teléfono 6.5 pulgadas, 128GB almacenamiento")
                    .sku("SKU-SM-002")
                    .precio(349990.0)
                    .activoProducto(true)
                    .fechaIngreso(LocalDate.of(2024, 4, 5))
                    .categoria(categoriaRepository.findById(1L).orElse(null))
                    .build());
        }
        if (!productoRepository.existsById(3L)) {
            productoRepository.save(ProductoModel.builder()
                    .id(null)
                    .nombreProducto("Cafetera Oster Digital")
                    .descripcion("Cafetera programable 12 tazas con temporizador")
                    .sku("SKU-CF-003")
                    .precio(49990.0)
                    .activoProducto(true)
                    .fechaIngreso(LocalDate.of(2024, 5, 10))
                    .categoria(categoriaRepository.findById(2L).orElse(null))
                    .build());
        }
        if (!productoRepository.existsById(4L)) {
            productoRepository.save(ProductoModel.builder()
                    .id(null)
                    .nombreProducto("Zapatillas Nike Air Max")
                    .descripcion("Zapatillas running talla 42, color negro")
                    .sku("SKU-ZP-004")
                    .precio(89990.0)
                    .activoProducto(true)
                    .fechaIngreso(LocalDate.of(2024, 6, 1))
                    .categoria(categoriaRepository.findById(3L).orElse(null))
                    .build());
        }
        System.out.println("DATOS iniciales de PRODUCTO cargados CORRECTAMENTE");
    }
}
