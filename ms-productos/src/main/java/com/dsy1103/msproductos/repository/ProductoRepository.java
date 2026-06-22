package com.dsy1103.msproductos.repository;

import com.dsy1103.msproductos.model.ProductoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductoRepository extends JpaRepository<ProductoModel, Long> {

    @Query(value = "SELECT * FROM producto p WHERE LOWER(p.nombre_producto) LIKE LOWER(CONCAT('%', :nombre, '%'))" +
            " AND p.precio < :precio", nativeQuery = true)
    List<ProductoModel> findByNombreContengaAndPrecioMenorQue(@Param("nombre") String nombre,@Param("precio") Double precio);
}
