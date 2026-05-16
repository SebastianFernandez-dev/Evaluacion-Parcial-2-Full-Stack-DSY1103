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

    private String nombre;

    private String codigo;

    private String descripcion;

    private String pais;

    private LocalDate fechaCreacion;
}
