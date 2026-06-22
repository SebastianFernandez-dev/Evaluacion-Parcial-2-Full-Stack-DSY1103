package com.dsy1103.msinventario.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description="Modelo espejo que representa la información comercial e identificatoria de un Producto importado desde el MS de Catálogo")
public class ProductoDTO {

    @Schema(description="ID único del producto en el catálogo central", example="1025")
    @Positive(message="El campo PRODUCTO ID no puede ser negativo")
    private Long id;

    @Schema(description="Nombre comercial asignado al artículo", minLength=6, maxLength=50, example="Teclado Mecánico RGB")
    @NotBlank(message="El campo NOMBRE PRODUCTO es obligatorio")
    @Size(min=6, max=50)
    private String nombre;

    @Schema(description="Especificaciones o descripción detallada de las características del producto", minLength=5, maxLength=200, example="Teclado con switches mecánicos táctiles e iluminación personalizable")
    @NotBlank(message="El campo DESCRIPCION PRODUCTO es obligatorio")
    @Size(min=5, max=200)
    private String descripcion;

    @Schema(description="Código de barras o SKU (Stock Keeping Unit) único para la venta minorista", minLength=8, maxLength=12, example="TEC-MECO-01")
    @NotBlank(message="El campo SKU PRODUCTO es obligatorio")
    @Size(min=8, max=12)
    private String sku;

    @Schema(description="Precio unitario de venta al público en la moneda local", minimum="0.0", example="89.99")
    @NotNull(message="El campo PRECIO PRODUCTO es obligatorio")
    @Positive(message="El campo PRECIO PRODUCTO no puede ser negativo")
    @DecimalMin("0.0")
    private Double precio;

    @Schema(description="Indica si el producto se encuentra actualmente vigente y disponible para la venta", example="true")
    @NotNull(message="El campo ACTIVO PRODUCTO es obligatorio")
    private Boolean activo;

    @Schema(description="Fecha exacta en la que el producto fue dado de alta en el sistema de catálogo", example="2025-01-10")
    @NotNull(message="El campo FECHA INGRESO PRODUCTO es obligatorio")
    @PastOrPresent(message="Debe ingresar FECHA actual o pasada")
    private LocalDate fechaIngreso;

    @Schema(description="ID de la categoría a la cual pertenece este producto dentro del catálogo general", example="4")
    @Positive(message="El campo PRODUCTO CATEGORIA ID no puede ser negativo")
    private Long categoriaId;
}
