package com.dsy1103.msproductos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="categoria")
public class CategoriaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre_categoria", nullable = false, length = 40)
    private String nombreCategoria;

    @Column(name = "descripcion", length = 200)
    private String descripcion;

    @Column(name = "codigo_categoria", nullable = false, length = 10)
    private String codigoCategoria;

    @Column(name = "activo_categoria", nullable = false)
    private Boolean activoCategoria;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDate fechaCreacion;

    @OneToMany(mappedBy = "categoria",
    cascade = CascadeType.ALL,
    fetch = FetchType.LAZY)
    private List<ProductoModel> listaProducto;
}
