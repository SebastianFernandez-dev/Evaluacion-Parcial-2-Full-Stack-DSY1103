package com.dsy1103.msreportes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="reporte")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReporteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="descripcion", nullable=false, length=120)
    private String descripcion;

    @Column(name="tipo", nullable=false, length=40)
    private String tipo;

    @Column(name="total_ventas", nullable=false)
    private Double totalVentas;

    @Column(name="cantidad_pedidos", nullable=false)
    private Integer cantidadPedidos;

    @Column(name="cantidad_pagos", nullable=false)
    private Integer cantidadPagos;

    @Column(name="fecha_generacion", nullable=false, insertable=false, updatable=false)
    private LocalDate fechaGeneracion;

    @Column(name="publicado", nullable=false)
    private Boolean publicado;

    @Column(name="usuario_id", nullable=false)
    private Long usuarioId;
}
