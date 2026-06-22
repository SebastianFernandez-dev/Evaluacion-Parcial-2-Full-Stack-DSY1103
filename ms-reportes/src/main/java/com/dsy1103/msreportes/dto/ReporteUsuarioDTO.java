package com.dsy1103.msreportes.dto;

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
public class ReporteUsuarioDTO {

    @Positive(message="El campo ID no puede ser negativo")
    private Long id;

    @NotBlank(message="El campo DESCRIPCION es obligatorio")
    @Size(min=3, max=120)
    private String descripcion;

    @NotBlank(message="El campo TIPO es obligatorio")
    @Size(min=3, max=40)
    private String tipo;

    @NotNull(message="El campo TOTAL VENTAS es obligatorio")
    @Positive(message="El campo TOTAL VENTAS no puede ser negativo")
    @DecimalMin("0.0")
    private Double totalVentas;

    @NotNull(message="El campo CANTIDAD PEDIDOS es obligatorio")
    @Positive(message="El campo CANTIDAD PEDIDOS no puede ser negativo")
    private Integer cantidadPedidos;

    @NotNull(message="El campo CANTIDAD PAGOS es obligatorio")
    @Positive(message="El campo CANTIDAD PAGOS no puede ser negativo")
    private Integer cantidadPagos;

    //este dato lo registra automaticamente la base de datos
    private LocalDate fechaGeneracion;

    @NotNull(message="El campo PUBLICADO es obligatorio")
    private Boolean publicado;

    @Positive(message="El campo USUARIO ID no puede ser negativo")
    private Long usuarioId;

    //datos de usuario
    @NotBlank(message="El campo PRIMER NOMBRE USUARIO es obligatorio")
    @Size(min=6, max=20)
    private String primerNombreUsuario;

    @NotBlank(message="El campo SEGUNDO NOMBRE USUARIO es obligatorio")
    @Size(min=6, max=20)
    private String segundoNombreUsuario;

    @NotBlank(message="El campo PRIMER APELLIDO USUARIO es obligatorio")
    @Size(min=6, max=20)
    private String primerApellidoUsuario;

    @NotBlank(message="El campo SEGUNDO APELLIDO USUARIO es obligatorio")
    @Size(min=6, max=20)
    private String segundoApellidoUsuario;

    @NotBlank(message="El campo CORREO USUARIO es obligatorio")
    @Email(message="Direccion de CORREO USUARIO no valida")
    @Size(max=80)
    private String correoUsuarioUsuario;

    @NotNull(message="El RUT USUARIO es obligatorio")
    @Positive(message="El RUT USUARIO debe ser valido")
    private Integer rutUsuario;

    @NotNull(message="El DV USUARIO es obligatorio")
    @Min(value=0, message="El DV USUARIO no puede ser menor a 0")
    @Max(value=9, message="El DV USUARIO no puede ser mayor a 9")
    private Integer dvRutUsuario;

    @NotNull(message="El campo ACTIVO USUARIO es obligatorio")
    private Boolean activoUsuario;

    @NotNull(message="El campo FECHA REGISTRO USUARIO es obligatorio")
    @PastOrPresent(message="Debe ingresar FECHA USUARIO actual o pasada")
    private LocalDate fechaRegistroUsuario;
}
