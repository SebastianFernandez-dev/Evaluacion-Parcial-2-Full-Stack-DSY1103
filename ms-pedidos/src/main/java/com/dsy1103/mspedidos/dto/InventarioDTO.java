package com.dsy1103.mspedidos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventarioDTO {
    private Long id;
    private String codigo;
    private String ubicacion;
    private Integer cantidadDisponible;
    private Integer stockMinimo;
    private Boolean activo;
    private LocalDate fechaRealizacion;
    private Long productoId;
}
