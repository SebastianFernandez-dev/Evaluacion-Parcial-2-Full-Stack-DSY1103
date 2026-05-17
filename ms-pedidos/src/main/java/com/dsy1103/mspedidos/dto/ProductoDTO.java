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
public class ProductoDTO {

    private Long id;
    private String nombreProducto;
    private String descripcion;
    private String sku;
    private Double precio;
    private Boolean activoProducto;
    private LocalDate fechaIngreso;

    private Long categoriaId;

}
