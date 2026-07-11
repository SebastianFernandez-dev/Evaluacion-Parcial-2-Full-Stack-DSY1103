package com.dsy1103.msenvios.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeguimientoRequestDTO {

    @NotNull(message = "El ID de envio es obligatorio")
    private Long envioId;

    @NotBlank(message = "El estado es obligatorio")
    @Size(min = 3, max = 30)
    private String estadoSegui;

    @NotBlank(message = "La ubicacion es obligatoria")
    @Size(min = 3, max = 120)
    private String ubiAtual;

    @NotBlank(message = "La observacion es obligatoria")
    @Size(min = 3, max = 150)
    private String observacion;

    @NotNull(message = "La fecha es obligatoria")
    @PastOrPresent(message = "fecha de seguimiento no puede ser una fecha futura")
    private LocalDateTime fechaSegui;

    @NotNull(message = "El campo visible es obligatorio")
    private Boolean visible;
}
