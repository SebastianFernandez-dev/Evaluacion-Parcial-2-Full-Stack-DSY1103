package com.dsy1103.msempleados.client;

import com.dsy1103.msempleados.dto.SucursalDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="ms-sucursales", url="http://localhost:8088") //Conexion a proyecto de sucursal
public interface SucursalClient {

    @GetMapping("/api/v1/sucursales/{id}")
    SucursalDTO obtenerSucursalPorId(@PathVariable Long id);
}
