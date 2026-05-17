package com.dsy1103.msenvios.Client;

import com.dsy1103.msenvios.dto.PedidoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-pedidos", url = "http://localhost:8084") // Le decimos cómo se llama el microservicio y a qué URL exacta debe llamar (Puerto 8084 de Pedidos)
public interface PedidoClient {

    // Le dices: Cuando use este metodo, haz un get a /api/v1/pedidos/{id}
    @GetMapping("/api/v1/pedidos/{id}")
    PedidoDTO obtenerPedidoPorId(@PathVariable("id")Long id);

}
