package com.dsy1103.msproductos.dto;

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
public class ProductoDTO {

    private Long id;

    @NotBlank(message = "El campo NOMBRE_PRODUCTO es obligatorio")
    @Size(min = 6,max = 50)
    private String nombreProducto;

    @NotBlank(message = "El campo DESCRIPCION es obligatorio")
    @Size(min = 5,max = 200)
    private String descripcion;

    @NotBlank(message = "El campo SKU es obligatorio")
    @Size(min = 8,max = 12)
    private String sku;

    @NotNull(message = "El campo PRECIO es obligatorio")
    @DecimalMin("0.0")
    private Double precio;

    @NotNull(message = "El campo ACTIVO_PRODUCTO es obligatorio")
    private Boolean activoProducto;

    @NotNull(message = "El campo FECHA_INGRESO es obligatorio")
    @PastOrPresent(message = "Debe ingresar FECHA actual o pasada")
    private LocalDate fechaIngreso;

    @NotNull(message = "El campo CATEGORIA_ID es obligatorio")
    @Positive(message = "El campo CATEGORIA_ID no puede ser negativo")
    private Long categoriaId;
}
