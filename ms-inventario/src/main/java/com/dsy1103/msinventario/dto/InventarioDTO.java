package com.dsy1103.msinventario.dto;

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
public class InventarioDTO {

    @Positive(message="El campo ID no puede ser negativo")
    private Long id;

    @NotBlank(message="El campo CODIGO es obligatorio")
    @Size(min=2, max=12)
    private String codigo;

    @NotBlank(message="El campo UBICACION es obligatorio")
    @Size(min=5, max=200)
    private String ubicacion;

    @NotNull(message="El campo CANTIDAD DISPONIBLE es obligatorio")
    @Positive(message="El campo CANTIDAD DISPONIBLE no puede ser negativo")
    private Integer cantidadDisponible;

    @NotNull(message="El campo STOCK MINIMO es obligatorio")
    @Positive(message="El campo STOCK MINIMO no puede ser negativo")
    private Integer stockMinimo;

    @NotNull(message="El campo ACTIVO es obligatorio")
    private Boolean activo;

    @NotNull(message="El campo FECHA REALIZACION es obligatorio")
    @PastOrPresent(message="Debe ingresar FECHA actual o pasada")
    private LocalDate fechaRealizacion;

    @Positive(message="El campo SUCURSAL ID no puede ser negativo")
    private Long sucursalId;

    @Positive(message="El campo PROVEEDOR ID no puede ser negativo")
    private Long proveedorId;

    @Positive(message="El campo PRODUCTO ID no puede ser negativo")
    private Long productoId;

    //lista de todos los movimientos asociados al inventario
    private List<MovimientoStockDTO> listaMovimientosStock;
}
