package com.dsy1103.msusuarios.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PERFIL")
public class PerfilModelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre_perfil",nullable = false,length = 60)
    private String nombrePerfil;

    @Column(name = "descripcion_perfil",nullable = true,length = 200)
    private String descripcion;

    @Column(name = "nivel_acceso_perfil",nullable = false)
    private Integer nivelAcessoPerfil;

    @Column(name = "activo_perfil",nullable = false,length = 1)
    private Boolean activo;

    @Column(name = "fecha_creacion_perfil",nullable = false)
    private LocalDate fechaCreacionPerfil;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false) // FK declarado
    private UsuarioModelo usuario;

}
