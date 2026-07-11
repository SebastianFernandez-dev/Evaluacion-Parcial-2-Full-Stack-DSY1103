package com.dsy1103.msproveedores.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProveedorResponseDTO {

    private Long id;
    private String nombre;
    private String razonSocial;
    private String documentoFiscal;
    private String correoContacto;
    private String ciudad;
    private Integer calificacion;
    private Boolean activo;
    private LocalDate fechaRegistro;
    private List<ContratoResponseDTO> listaContrato;
}
