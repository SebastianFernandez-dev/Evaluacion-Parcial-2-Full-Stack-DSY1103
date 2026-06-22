package com.dsy1103.msempleados.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "empleado")
public class EmpleadoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "primer_nombre", nullable = false, length = 20)
    private String primerNombre;

    @Column(name = "segundo_nombre", length = 20)
    private String segundoNombre;

    @Column(name = "primer_apellido", nullable = false, length = 20)
    private String primerApellido;

    @Column(name = "segundo_apellido", nullable = false, length = 20)
    private String segundoApellido;

    @Column(name = "cargo", nullable = false, length = 60)
    private String cargo;

    @Column(name = "rut", nullable = false, length = 8)
    private Integer rut;

    //Implementar condicion que solo permita "k" en caso de...
    @Column(name = "dv_rut", nullable = false, length = 1)
    private String dvRut;

    @Column(name = "correo_empleado", nullable = false, length = 80)
    private String correoEmpleado;

    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDate fechaIngreso;

    @Column(name = "activo_empleado", nullable = false)
    private Boolean activoEmpleado;

    // fk sucursal, la cual es de otra base de datos
    @Column(name = "sucursal_id", nullable = false)
    private Long sucursalId;
}
