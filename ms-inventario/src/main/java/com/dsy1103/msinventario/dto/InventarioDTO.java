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
@Schema(description="Modelo de transferencia de datos que representa el estado físico y logístico de un artículo en el inventario")
public class InventarioDTO {

    @Schema(description="Identificador único del registro de inventario (Autogenerado)", example="1", accessMode=Schema.AccessMode.READ_ONLY)
    @Positive(message="El campo ID no puede ser negativo")
    private Long id;

    @Schema(description="Código alfanumérico único de identificación del inventario", minLength=2, maxLength=12, example="INV-2026-00A")
    @NotBlank(message="El campo CODIGO es obligatorio")
    @Size(min=2, max=12)
    private String codigo;

    @Schema(description="Dirección física, pasillo o sección del almacén donde se resguarda el stock", minLength=5, maxLength=200, example="Pasillo B, Estante 4, Nivel Superior")
    @NotBlank(message="El campo UBICACION es obligatorio")
    @Size(min=5, max=200)
    private String ubicacion;

    @Schema(description="Cantidad total de unidades físicas disponibles actualmente en el almacén", minimum="1", example="450")
    @NotNull(message="El campo CANTIDAD DISPONIBLE es obligatorio")
    @Positive(message="El campo CANTIDAD DISPONIBLE no puede ser negativo")
    private Integer cantidadDisponible;

    @Schema(description="Límite mínimo de existencias permitido antes de disparar una alerta de reabastecimiento", minimum="1", example="50")
    @NotNull(message="El campo STOCK MINIMO es obligatorio")
    @Positive(message="El campo STOCK MINIMO no puede ser negativo")
    private Integer stockMinimo;

    @Schema(description="Define si el registro de inventario está operativo y disponible para transacciones", example="true")
    @NotNull(message="El campo ACTIVO es obligatorio")
    private Boolean activo;

    @Schema(description="Fecha en la que se realizó el último conteo físico o actualización del registro", example="2026-06-14")
    @NotNull(message="El campo FECHA REALIZACION es obligatorio")
    @PastOrPresent(message="Debe ingresar FECHA actual o pasada")
    private LocalDate fechaRealizacion;

    @Schema(description="ID del producto asociado, gestionado de forma externa por el microservicio de Productos", example="1025")
    @Positive(message="El campo PRODUCTO ID no puede ser negativo")
    private Long productoId;

    //lista de todos los movimientos asociados al inventario
    @Schema(description="Historial completo de movimientos de stock (Kardex: entradas y salidas) vinculados directamente a este lote de inventario")
    private List<MovimientoStockDTO> listaMovimientosStock;
}
