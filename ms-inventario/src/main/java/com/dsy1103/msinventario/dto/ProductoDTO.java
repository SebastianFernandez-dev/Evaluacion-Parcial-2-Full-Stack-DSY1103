package com.dsy1103.msinventario.dto;

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

    @Positive(message="El campo PRODUCTO ID no puede ser negativo")
    private Long Id;

    @NotBlank(message="El campo NOMBRE PRODUCTO es obligatorio")
    @Size(min=6, max=50)
    private String nombre;

    @NotBlank(message="El campo DESCRIPCION PRODUCTO es obligatorio")
    @Size(min=5, max=200)
    private String descripcion;

    @NotBlank(message="El campo SKU PRODUCTO es obligatorio")
    @Size(min=8, max=12)
    private String sku;

    @NotNull(message="El campo PRECIO PRODUCTO es obligatorio")
    @Positive(message="El campo PRECIO PRODUCTO no puede ser negativo")
    @DecimalMin("0.0")
    private Double precio;

    @NotNull(message="El campo ACTIVO PRODUCTO es obligatorio")
    private Boolean activo;

    @NotNull(message="El campo FECHA INGRESO PRODUCTO es obligatorio")
    @PastOrPresent(message="Debe ingresar FECHA actual o pasada")
    private LocalDate fechaIngreso;

    @Positive(message="El campo PRODUCTO CATEGORIA ID no puede ser negativo")
    private Long categoriaId;
}
