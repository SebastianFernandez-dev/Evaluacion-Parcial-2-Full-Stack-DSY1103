package com.dsy1103.mssucursales.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

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

    private String nombre;

    private String codigo;

    private String direccion;

    private Integer capacidadAtencion;

    private Boolean activo;

    private LocalDate fechaApertura;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_region_id")
    private RegionModel region;
}
