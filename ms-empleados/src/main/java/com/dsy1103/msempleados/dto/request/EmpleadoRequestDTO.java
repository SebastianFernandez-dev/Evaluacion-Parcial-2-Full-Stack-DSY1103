package com.dsy1103.msempleados.dto.request;

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
public class EmpleadoRequestDTO {

    @NotBlank(message="El campo PRIMER NOMBRE es obligatorio")
    @Size(min=2,max=20)
    private String primerNombre;

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
    private Integer rut;

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

    private Long sucursalId;
}
