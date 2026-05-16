package com.dsy1103.msenvios.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class EnvioModelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "codigo_envio",nullable = false, length = 40)
    private String codigoEnvio;

    @Column(name = "pedido_Id",nullable = false)
    private Long pedidoId;

    @Column(name = "usuario_id",nullable = false)
    private Long usuarioId;

    @Column(name = "direccion_destino",nullable = false, length = 200)
    private String direccionDestino;

    @Column(name = "estado_envio",nullable = false, length = 30)
    private String estadoEnvio;

    @Column(name = "fecha_salida",nullable = false)
    private LocalDateTime fechaSalida;

    @Column(name = "fecha_entrega_estimada",nullable = false)
    private LocalDate fechaEntregaEstimada;

    @Column(name = "fecha_entrega",nullable = false)
    private LocalDate fechaEntregado;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @OneToMany(mappedBy = "envio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private java.util.List<SeguimientoModelo> seguimientos;


}
