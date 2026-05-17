package com.dsy1103.mspedidos.Client;


import com.dsy1103.mspedidos.dto.ProductoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-productos", url = "http://localhost:8082/api/v1/productos")
public interface ProductoClient {

    @GetMapping("/{id}")
    ProductoDTO obtenerProductoPorId(@PathVariable("id") Long id);

}
