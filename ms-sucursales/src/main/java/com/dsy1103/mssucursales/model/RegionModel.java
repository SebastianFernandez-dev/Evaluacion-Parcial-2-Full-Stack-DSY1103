package com.dsy1103.mssucursales.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="region")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegionModel {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="nombre", nullable=false, length=80, unique=true)
    private String nombre;

    @Column(name="codigo", nullable=false, length=20)
    private String codigo;

    @Column(name="descripcion", nullable=true, length=200)
    private String descripcion;

    @Column(name="pais", nullable=false, length=80)
    private String pais;

    @Column(name="fecha_creacion", nullable=false)
    private LocalDate fechaCreacion;
}
