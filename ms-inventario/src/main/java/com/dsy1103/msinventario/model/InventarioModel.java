package com.dsy1103.msinventario.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="inventario")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="codigo", nullable=false, length=12, unique=true)
    private String codigo;

    @Column(name="ubicacion", nullable=false, length=100)
    private String ubicacion;

    @Column(name="cantidad_disponible", nullable=false)
    private Integer cantidadDisponible;

    @Column(name="stock_minimo", nullable=false)
    private Integer stockMinimo;

    @Column(name="activo", nullable=false)
    private Boolean activo;

    @Column(name="fecha_realizacion", nullable=false)
    private LocalDate fechaRealizacion;

    @Column(name="producto_id", nullable=false)
    private Long productoId;

    @OneToMany(mappedBy="inventario",
            cascade=CascadeType.ALL,
            fetch=FetchType.LAZY)
    private List<MovimientoStockModel> listaMovimientosStock = new ArrayList<>();
}
