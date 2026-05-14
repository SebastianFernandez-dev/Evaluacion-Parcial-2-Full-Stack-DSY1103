package com.dsy1103.msempleados.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoDTO {

    private Long id;

    @NotBlank(message="El campo PRIMER NOMBRE es obligatorio")
    @Size(min=2,max=20)
    private String primerNombre;

    @NotBlank(message="El campo SEGUNDO NOMBRE es obligatorio")
    @Size(min=2,max=20)
    private String segundoNombre;

    @NotBlank(message="El campo SEGUNDO NOMBRE es obligatorio")
    @Size(min=2,max=20)
    private String primerApellido;

    private String segundoApellido;

    private String cargo;

    private Integer rut;

    //Implementar condicion que solo permita "k" en caso de...
    private String dvRut;

    private String correoEmpleado;

    private LocalDate fechaIngreso;

    private boolean activoEmpleado=true;

    // fk sucursal, la cual es de otra base de datos
    private Long sucursalId;
}
