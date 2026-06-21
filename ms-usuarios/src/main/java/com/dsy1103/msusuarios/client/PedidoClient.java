package com.dsy1103.msusuarios.client;

import com.dsy1103.msusuarios.dto.PedidoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ms-pedidos", url = "http://localhost:8084/api/v1/pedidos")
public interface PedidoClient {

    @GetMapping("/usuario/{usuarioId}")
    List<PedidoDTO> obtenerPedidosPorUsuarioId(@PathVariable("usuarioId") Long usuarioId);
}
