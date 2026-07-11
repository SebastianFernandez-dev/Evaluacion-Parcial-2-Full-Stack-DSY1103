package com.dsy1103.msempleados.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoResponseDTO {

    private Long id;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String cargo;
    private Integer rut;
    private String dvRut;
    private String correoEmpleado;
    private LocalDate fechaIngreso;
    private Boolean activoEmpleado;
    private Long sucursalId;
}
