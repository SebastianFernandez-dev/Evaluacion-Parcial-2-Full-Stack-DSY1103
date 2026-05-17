package com.dsy1103.msinventario.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="movimiento_stock")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoStockModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="tipo", nullable=false, length=20)
    private String tipo;

    @Column(name="cantidad", nullable=false)
    private Integer cantidad;

    @Column(name="motivo", nullable=false, length=150)
    private String motivo;

    @Column(name="saldo_posterior", nullable=false)
    private Integer saldoPosterior;

    @Column(name="fecha", nullable=false)
    private LocalDate fecha;

    @Column(name="aprobado", nullable=false)
    private Boolean aprobado;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_inventario_id")
    private InventarioModel inventario;
}
