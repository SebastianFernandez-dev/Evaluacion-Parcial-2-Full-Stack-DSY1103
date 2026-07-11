package com.dsy1103.msreportes.client;

import com.dsy1103.msreportes.dto.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="servicio-usuario", url="http://localhost:8080")
public interface UsuarioClient {

    @GetMapping("/api/v1/usuarios/{id}")
    UsuarioDTO obtenerUsuarioPorId(@PathVariable Long id);
}
