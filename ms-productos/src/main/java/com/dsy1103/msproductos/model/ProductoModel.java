package com.dsy1103.msproductos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "producto")
public class ProductoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre_producto", nullable = false, length = 50)
    private String nombreProducto;

    @Column(name = "descripcion", length = 200)
    private String descripcion;

    @Column(name = "sku_producto", nullable = false, length = 21)
    private String sku;

    @Column(name = "precio_producto", nullable = false)
    private Double precio;

    @Column(name = "activo_proveedor", nullable = false)
    private Boolean activoProducto;

    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDate fechaIngreso;

    @ManyToOne
    @JoinColumn(name = "fk_categoria_id")
    private CategoriaModel categoria;
}
