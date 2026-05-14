package com.dsy1103.msproveedores.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="contrato")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContratoModel {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="numero", nullable=false, length=40, unique=true)
    private String numero;

    @Column(name="tipo", nullable=false, length=30)
    private String tipo;

    @Column(name="valor", nullable=false)
    private Double valor;

    @Column(name="fecha_inicio", nullable=false)
    private LocalDate fechaInicio;

    @Column(name="fecha_fin", nullable=false)
    private LocalDate fechaFin;

    @Column(name="vigente", nullable=false)
    private Boolean vigente;

    @Column(name="observaciones", nullable=false, length=200)
    private String observaciones;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_proveedor_id")
    private ProveedorModel proveedor;
}
