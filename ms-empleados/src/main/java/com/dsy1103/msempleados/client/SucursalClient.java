package com.dsy1103.msempleados.client;

import com.dsy1103.msempleados.dto.SucursalDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="ms-sucursales", url="${sucursal.service.url}")
public interface SucursalClient {

    @GetMapping("/api/v1/sucursal/{id}")
    SucursalDTO obtenerSucursalPorId(@PathVariable Long id);
}