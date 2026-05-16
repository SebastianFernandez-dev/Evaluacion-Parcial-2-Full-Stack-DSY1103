package com.dsy1103.msenvios.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class SeguimientoModelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "estado",nullable = false,length = 120)
    private String estadoSegui;

    @Column(name = "ubicacion_actual",nullable = false)
    private String ubiAtual;

    @Column(name = "observacion",nullable = false, length = 200)
    private String observacion;

    @Column(name = "fecha_seguimiento",nullable = false)
    private LocalDateTime fechaSegui;

    @Column(name = "visible",nullable = false)
    private Boolean visible;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "envio_id", nullable = false) // FK declarado
    private EnvioModelo envio;

}
