package com.dsy1103.msusuarios.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USUARIO")
public class UsuarioModelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Long id;

    @Column(name = "primer_nombre",nullable = false,length = 20)
    private String primerNombre;

    @Column(name = "segundo_nombre",nullable = false,length = 20)
    private String segundoNombre;

    @Column(name = "primer_apellido",nullable = false,length = 20)
    private String primerApellido;

    @Column(name = "segundo_apellido",nullable = false,length = 20)
    private String segundoApellido;

    @Column(name = "correo_usuario",nullable = false,length = 80,unique = true)
    private String correoUsuario;

    @Column(name = "rut_usuario",nullable = false)
    private Integer rut;

    @Column(name = "dv_rut_usuario",length = 1, nullable = false) //falta completar
    private Integer dvRut;

    @Column(name = "fecha_registro_usuario", nullable =false)
    private LocalDate fechaRegistro;

    @Column(name = "activo_usuario", nullable = false)
    private Boolean activo;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private java.util.List<PerfilModelo> perfiles;
}
