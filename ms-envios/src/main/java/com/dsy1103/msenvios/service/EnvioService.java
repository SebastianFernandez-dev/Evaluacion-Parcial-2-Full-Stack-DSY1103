package com.dsy1103.msenvios.service;


import com.dsy1103.msenvios.Client.PedidoClient;
import com.dsy1103.msenvios.Client.UsuarioClient;
import com.dsy1103.msenvios.dto.PedidoDTO;
import com.dsy1103.msenvios.dto.UsuarioDTO;
import com.dsy1103.msenvios.modelo.EnvioModelo;
import com.dsy1103.msenvios.dto.EnvioDTO;
import com.dsy1103.msenvios.mapper.EnvioMapper;
import com.dsy1103.msenvios.repository.EnvioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
public class EnvioService {

    @Autowired
    private EnvioRepository envioRepository;
    @Autowired
    private EnvioMapper envioMapper;
    @Autowired
    private PedidoClient pedidoClient;
    @Autowired
    private UsuarioClient usuarioClient;


    @Transactional(readOnly = true)
    public List<EnvioDTO> listarTodos() {
        log.info("Iniciando consulta de todos los Envios");
        return envioRepository.findAll().stream()
                .map(envio -> envioMapper.toDTO(envio)) // Uso de la instancia inyectada
                .collect(Collectors.toList());
    }

    @Transactional
    public EnvioDTO crear(EnvioDTO dto) {
        try {
            log.info("Servicio: Intentando crear un nuevo envío para el pedido ID: {}", dto.getPedidoId());
            // Levantamos el teléfono y llamamos al otro microservicio (al puerto 8084)
            // Le pasamos el 'pedidoId' que nos mandaron desde Postman
            PedidoDTO pedidoRemoto = pedidoClient.obtenerPedidoPorId(dto.getPedidoId());

            // Si la otra oficina nos dice que no encuentra nada (null), frenamos el proceso de inmediato
            if (pedidoRemoto == null) {
                throw new RuntimeException("Error: El pedido con ID " + dto.getPedidoId() + " no existe en el microservicio de Pedidos.");
            }

            // Llamada 2: Validar Usuario
            // Le pasamos el 'usuarioId' que viene en el JSON de Postman
            UsuarioDTO usuarioRemoto = usuarioClient.obtenerUsuarioPorId(dto.getUsuarioId());
            if (usuarioRemoto == null) {
                throw new RuntimeException("El usuario con ID " + dto.getUsuarioId() + " no existe en el sistema.");
            }

            EnvioModelo modelo = envioMapper.toEntity(dto);
            EnvioModelo guardado = envioRepository.save(modelo);
            return envioMapper.toDTO(guardado);
        } catch (Exception e) {
            log.error("Error al crear Envio: {}", e.getMessage());
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public EnvioDTO buscarPorId(Long id) {
        log.info("Buscando Envio con ID: {}", id);
        EnvioModelo envio = envioRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Envio con ID {} no encontrado", id);
                    return new EntityNotFoundException("Envio no encontrado con ID: " + id);
                });
        return envioMapper.toDTO(envio);
    }

    @Transactional
    public void actualizar(EnvioDTO eDTO) {
        log.info("Actualizando ENVIO con ID: {}", eDTO.getId());

        envioRepository.findById(eDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Error: ENVIO no encontrado para actualizar."));

        envioRepository.save(EnvioModelo.builder()
                .id(eDTO.getId()) // Clave para que Hibernate haga un UPDATE
                .codigoEnvio(eDTO.getCodigoEnvio())
                .pedidoId(eDTO.getPedidoId())
                .usuarioId(eDTO.getUsuarioId())
                .direccionDestino(eDTO.getDireccionDestino())
                .estadoEnvio(eDTO.getEstadoEnvio())
                .fechaSalida(eDTO.getFechaSalida())
                .fechaEntregaEstimada(eDTO.getFechaEntregaEstimada())
                .fechaEntregado(eDTO.getFechaEntregado())
                .activo(eDTO.getActivo())
                .build());
    }

    @Transactional
    public void eliminar(Long id) {
        log.info("Solicitud para eliminar Envio ID: {}", id);
        if (!envioRepository.existsById(id)) {
            log.error("Error al eliminar, ID {} no existe", id);
            throw new EntityNotFoundException("No se puede eliminar: Envio no encontrado");
        }
        envioRepository.deleteById(id);
        log.info("Envio ID: {} eliminado correctamente", id);
    }

    //metodo para obtener envios no entregados
    public List<EnvioModelo> obtenerEnviosEnRangoNoEntregados(LocalDateTime inicio, LocalDateTime fin) {
        return envioRepository.findEnviosEnRangoNoEntregados(inicio, fin);
    }


}
