package com.dsy1103.mspedidos.service;

import com.dsy1103.mspedidos.dto.DetallePedidoDTO;
import com.dsy1103.mspedidos.dto.PedidoDTO;
import com.dsy1103.mspedidos.mapper.DetallePedidoMapper;
import com.dsy1103.mspedidos.modelo.DetallePedidoModelo;
import com.dsy1103.mspedidos.modelo.PedidoModelo;
import com.dsy1103.mspedidos.repository.DetallePedidoRepository;
import com.dsy1103.mspedidos.repository.PedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DetallePedidoService {

    @Autowired
    private DetallePedidoRepository detallePedidoRepo;

    @Autowired
    private PedidoRepository pedidoRepo;

    @Autowired
    private DetallePedidoMapper detallePedidoMapper;

    @Transactional(readOnly = true)
    public List<DetallePedidoDTO> listarTodos() {
        log.info("Iniciando consulta de todos los Detalles Pedidos");
        return detallePedidoRepo.findAll().stream()
                .map(detalle -> detallePedidoMapper.toDTO(detalle)) // Uso de la instancia inyectada
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DetallePedidoDTO buscarPorId(Long id) {
        log.info("Buscando Detalle Pedido con ID: {}", id);
        DetallePedidoModelo detallePedido = detallePedidoRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Detalle Pedido con ID {} no encontrado", id);
                    return new EntityNotFoundException("Detalle Pedido no encontrado con ID: " + id);
                });
        return detallePedidoMapper.toDTO(detallePedido);
    }

    @Transactional
    public DetallePedidoDTO crear(DetallePedidoDTO dto) {
        try {
            log.info("Creando Detalle Pedido ID {} al pedido ID {} ", dto.getProductoId(), dto.getPedidoId());

            // validamos que el pedido existe
            PedidoModelo pedido = pedidoRepo.findById(dto.getPedidoId())
                    .orElseThrow(() -> new EntityNotFoundException("No se puede crear el detalle: El pedido no existe"));

            DetallePedidoModelo modelo = detallePedidoMapper.toEntity(dto, pedido);

            DetallePedidoModelo guardado = detallePedidoRepo.save(modelo);
            log.info("Detalle Pedido creado con éxito. ID: {}", guardado.getId());

            return detallePedidoMapper.toDTO(guardado);

        } catch (Exception e) {
            log.error("Error al crear Detalle Pedido: {}", e.getMessage());
            throw e;
        }
    }

    @Transactional
    public DetallePedidoDTO actualizar(Long id, DetallePedidoDTO dto) {
        try {
            log.info("Iniciando actualización de Detalle Pedido con ID: {}", id);

            DetallePedidoModelo detalleExistente = detallePedidoRepo.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("No se puede actualizar: ID " + id + " no encontrado"));

            detalleExistente.setProductoId(dto.getProductoId());
            detalleExistente.setCantidadPedido(dto.getCantidadPedido());
            detalleExistente.setPrecioUnitario(dto.getPrecioUnitario());
            detalleExistente.setSubtotal(dto.getSubtotal());
            detalleExistente.setObservacion(dto.getObservacion());
            detalleExistente.setFechaRegistro(dto.getFechaRegistro());
            detalleExistente.setEstadoDetalle(dto.getEstadoDetalle());

            DetallePedidoModelo actualizado = detallePedidoRepo.save(detalleExistente);
            log.info("Usuario con ID: {} actualizado exitosamente", id);

            return detallePedidoMapper.toDTO(actualizado);

        } catch (Exception e) {
            log.error("Error al actualizar usuario ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Transactional
    public void eliminar(Long id) {
        log.info("Solicitud para eliminar Detalle Pedido ID: {}", id);
        if (!detallePedidoRepo.existsById(id)) {
            log.error("Error al eliminar, ID {} no existe", id);
            throw new EntityNotFoundException("No se puede eliminar: Detalle Pedido no encontrado");
        }
        detallePedidoRepo.deleteById(id);
        log.info("Detalle Pedido ID: {} eliminado correctamente", id);
    }

}
