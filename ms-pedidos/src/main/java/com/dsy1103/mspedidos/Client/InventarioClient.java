package com.dsy1103.mspedidos.Client;
import com.dsy1103.mspedidos.dto.InventarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "ms-inventario", url = "http://localhost:8083/api/v1/inventarios")
public interface InventarioClient {

    @GetMapping("/producto/{productoId}")
    InventarioDTO obtenerInventarioPorProductoId(@PathVariable("productoId") Long productoId);
}
