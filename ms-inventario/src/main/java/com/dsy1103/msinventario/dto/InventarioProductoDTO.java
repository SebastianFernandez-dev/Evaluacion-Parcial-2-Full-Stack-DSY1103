package com.dsy1103.msinventario.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
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
@Schema(description="Modelo de vista unificado que consolida la información física del inventario junto con los datos comerciales del producto asociado")
public class InventarioProductoDTO {

    // SECCIÓN: DATOS PROPIOS DEL INVENTARIO

    @Schema(description="ID único del registro de inventario", example="1")
    @Positive(message="El campo ID no puede ser negativo")
    private Long id;

    @Schema(description="Código alfanumérico único del lote en inventario", minLength=2, maxLength=12, example="INV-2026-00A")
    @NotBlank(message="El campo CODIGO es obligatorio")
    @Size(min=2, max=12)
    private String codigo;

    @Schema(description="Ubicación o pasillo físico dentro de las bodegas", minLength=5, maxLength=200, example="Pasillo B, Estante 4")
    @NotBlank(message="El campo UBICACION es obligatorio")
    @Size(min=5, max=200)
    private String ubicacion;

    @Schema(description="Existencias físicas actuales del artículo en almacén", minimum="1", example="450")
    @NotNull(message="El campo CANTIDAD DISPONIBLE es obligatorio")
    @Positive(message="El campo CANTIDAD DISPONIBLE no puede ser negativo")
    private Integer cantidadDisponible;

    @Schema(description="Cantidad mínima requerida para evitar quiebres de stock", minimum="1", example="50")
    @NotNull(message="El campo STOCK MINIMO es obligatorio")
    @Positive(message="El campo STOCK MINIMO no puede ser negativo")
    private Integer stockMinimo;

    @Schema(description="Estado operativo del lote de inventario", example="true")
    @NotNull(message="El campo ACTIVO es obligatorio")
    private Boolean activo;

    @Schema(description="Fecha del último conteo de stock o auditoría del lote", example="2026-06-14")
    @NotNull(message="El campo FECHA REALIZACION es obligatorio")
    @PastOrPresent(message="Debe ingresar FECHA actual o pasada")
    private LocalDate fechaRealizacion;

    @Schema(description="ID de relación técnica hacia el Producto en el catálogo central", example="1025")
    @Positive(message="El campo PRODUCTO ID no puede ser negativo")
    private Long productoId;

    // SECCIÓN: DATOS EXTRADITADOS DEL PRODUCTO

    @Schema(description="Nombre comercial del producto (Proveniente del MS Productos)", minLength=6, maxLength=50, example="Teclado Mecánico RGB")
    @NotBlank(message="El campo NOMBRE PRODUCTO es obligatorio")
    @Size(min=6, max=50)
    private String nombreProducto;

    @Schema(description="Descripción de las características del artículo", minLength=5, maxLength=200, example="Teclado con switches mecánicos táctiles e iluminación personalizable")
    @NotBlank(message="El campo DESCRIPCION PRODUCTO es obligatorio")
    @Size(min=5, max=200)
    private String descripcionProducto;

    @Schema(description="Código de barra o SKU identificador de venta minorista", minLength=8, maxLength=12, example="TEC-MECO-01")
    @NotBlank(message="El campo SKU PRODUCTO es obligatorio")
    @Size(min=8, max=12)
    private String skuProducto;

    @Schema(description="Precio unitario asignado al público", minimum="0.0", example="89.99")
    @NotNull(message="El campo PRECIO PRODUCTO es obligatorio")
    @Positive(message="El campo PRECIO PRODUCTO no puede ser negativo")
    @DecimalMin("0.0")
    private Double precioProducto;

    @Schema(description="Indica si el producto está vigente y habilitado para comercializarse", example="true")
    @NotNull(message="El campo ACTIVO PRODUCTO es obligatorio")
    private Boolean activoProducto;

    @Schema(description="Fecha de alta original del producto en el catálogo", example="2025-01-10")
    @NotNull(message="El campo FECHA INGRESO PRODUCTO es obligatorio")
    @PastOrPresent(message="Debe ingresar FECHA actual o pasada")
    private LocalDate fechaIngresoProducto;

    @Schema(description="ID de la categoría a la que pertenece el producto en el catálogo", example="4")
    @Positive(message="El campo PRODUCTO CATEGORIA ID no puede ser negativo")
    private Long productoCategoriaId;

    //SECCIÓN: COMPONENTES ANIDADOS

    @Schema(description="Listado cronológico del historial de movimientos y transacciones que afectaron a este inventario")
    private List<MovimientoStockDTO> listaMovimientosStock;
}
