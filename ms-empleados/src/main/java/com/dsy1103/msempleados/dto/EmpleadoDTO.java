package com.dsy1103.msempleados.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
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

    @NotBlank(message="El campo PRIMER APELLIDO es obligatorio")
    @Size(min=2,max=20)
    private String primerApellido;

    @NotBlank(message="El campo SEGUNDO APELLIDO es obligatorio")
    @Size(min=2,max=20)
    private String segundoApellido;

    @NotBlank(message="El campo CARGO es obligatorio")
    @Size(min=3,max=60)
    private String cargo;

    @NotNull(message="El campo RUT es obligatorio")
    @Min(value=1000000)
    @Max(value=99999999)
    @Size(min=7,max=8)
    private Integer rut;

    //Implementar condicion que solo permita "k" en caso de...
    @NotBlank(message="El campo DV_RUT es obligatorio")
    @Size(min=1,max=1)
    private String dvRut;

    @NotBlank(message="El campo CORREO_EMPLEADO es obligatorio")
    @Size(max=80)
    private String correoEmpleado;

    @NotNull(message="El campo FECHA_INGRESO es obligatorio")
    @PastOrPresent
    private LocalDate fechaIngreso;

    @NotNull(message="El campo campo ACTIVO_EMPLEADO es obligatorio")
    private Boolean activoEmpleado;

    // fk sucursal, la cual es de otra base de datos
    private Long sucursalId;
}
