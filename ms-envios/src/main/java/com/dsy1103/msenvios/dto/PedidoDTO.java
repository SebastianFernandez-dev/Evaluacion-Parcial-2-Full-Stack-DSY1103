package com.dsy1103.msenvios.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {

    // PedidoDTO Feign recibir la respuestas, datos que vienen desde otro Microservicio

    private Long id;
    private String estado;
    private Long usuarioId;


}
