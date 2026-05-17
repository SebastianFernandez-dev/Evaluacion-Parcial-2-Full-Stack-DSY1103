package com.dsy1103.msproductos.dto;

import com.dsy1103.msproductos.model.ProductoModel;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaDTO {

    @Positive(message = "El campo ID no puede ser negativo")
    private Long id;

    @NotBlank(message = "El campo NOMBRE es obligatorio")
    @Size(min=6,max=40)
    private String nombreCategoria;

    @NotBlank(message = "El campo DESCRIPCION es obligatorio")
    @Size(min=5,max = 200)
    private String descripcion;

    @NotBlank(message = "El campo CODIGO_CATEGORIA es obligatorio")
    @Size(min = 4,max = 10)
    private String codigoCategoria;

    @NotNull(message = "El campo ACTIVO_CATEGORIA es obligatorio")
    private Boolean activoCategoria;

    @NotNull(message = "El campo FECHA_CREACION es obligatorio")
    @PastOrPresent(message = "Debe ingresar FECHA actual o pasada")
    private LocalDate fechaCreacion;

    private List<ProductoModel> listaProducto;
}
