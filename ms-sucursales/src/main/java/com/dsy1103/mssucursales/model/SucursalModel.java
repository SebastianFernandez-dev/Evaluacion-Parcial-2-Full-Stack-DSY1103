package com.dsy1103.mssucursales.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="sucursal")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SucursalModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="nombre", nullable=false, length=80, unique=true)
    private String nombre;

    @Column(name="codigo", nullable=false, length=20, unique=true)
    private String codigo;

    @Column(name="direccion", nullable=false, length=200)
    private String direccion;

    @Column(name="capacidad_atencion", nullable=false)
    private Integer capacidadAtencion;

    @Column(name="activo", nullable=false)
    private Boolean activo;

    @Column(name="fecha_apertura", nullable=false)
    private LocalDate fechaApertura;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_region_id")
    private RegionModel region;
}
