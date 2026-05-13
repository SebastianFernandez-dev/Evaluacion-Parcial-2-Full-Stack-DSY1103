package com.dsy1103.msproveedores.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProveedorDTO {

    private Long id;

    @NotBlank(message="El campo RAZON SOCIAL es obligatorio")
    @Size(min=5, max=120)
    private String razonSocial;

    @NotBlank(message="El campo DOCUMENTO fiscal es obligatorio")
    @Size(min=5, max=30)
    private String documentoFiscal;

    @NotBlank(message="El campo CORREO es obligatorio")
    @Email(message="Direccion de CORREO no valida")
    @Size(max=80)
    private String correoContacto;

    @NotBlank(message="El campo CIUDAD es obligatorio")
    @Size(min=5, max=80)
    private String ciudad;

    @Positive(message="El campo CALIFICACION no puede ser negativo")
    @Min(1)
    @Max(5)
    private Integer calificacion;

    @NotNull(message="El campo ACCTIVO es obligatorio")
    private Boolean activo;

    @NotNull(message="El campo FECHA REGISTRO es obligatorio")
    @PastOrPresent(message="Debe ingresar FECHA actual o pasada")
    private LocalDate fechaRegistro;

    private List<ContratoDTO> listaContrato;
}
