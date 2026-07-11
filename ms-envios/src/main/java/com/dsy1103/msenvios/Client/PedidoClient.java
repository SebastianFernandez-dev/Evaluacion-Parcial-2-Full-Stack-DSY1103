package com.dsy1103.msenvios.Client;

import com.dsy1103.msenvios.dto.PedidoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-pedidos")
public interface PedidoClient {

    @GetMapping("/api/v1/pedidos/{id}")
    PedidoDTO obtenerPedidoPorId(@PathVariable("id") Long id);
}
