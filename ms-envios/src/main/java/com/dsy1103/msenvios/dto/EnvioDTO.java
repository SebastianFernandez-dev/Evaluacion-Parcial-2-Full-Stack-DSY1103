package com.dsy1103.msenvios.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnvioDTO {

    private Long id;

    @NotBlank(message = "El código de envío es obligatorio")
    @Size(min = 4, max = 40, message = "El código debe tener entre 4 y 40 caracteres")
    private String codigoEnvio;

    @NotNull(message = "El ID de pedido es obligatorio")
    @Positive(message = "El ID de pedido debe ser un número positivo")
    private Long pedidoId;

    @NotNull(message = "El ID de usuario es obligatorio")
    @Positive(message = "El ID de usuario debe ser un número positivo")
    private Long usuarioId;

    @NotBlank(message = "La dirección de destino es obligatoria")
    @Size(min = 5, max = 200, message = "La dirección debe tener entre 5 y 200 caracteres")
    private String direccionDestino;

    @NotBlank(message = "El estado de envío es obligatorio")
    @Size(min = 3, max = 30, message = "El estado debe tener entre 3 y 30 caracteres")
    private String estadoEnvio;

    @NotNull(message = "La fecha de salida es obligatoria")
    @PastOrPresent(message = "La fecha de salida no puede ser futura")
    private LocalDateTime fechaSalida;

    @NotNull(message = "La fecha de entrega estimada es obligatoria")
    @Future(message = "La fecha estimada debe ser una fecha futura")
    private LocalDate fechaEntregaEstimada;

    @NotNull(message = "La fecha de entrega es obligatoria")
    @PastOrPresent(message = "La fecha de entrega no puede ser futura")
    private LocalDate fechaEntregado;

    @NotNull(message = "El estado activo es obligatorio")
    private Boolean activo;

}
