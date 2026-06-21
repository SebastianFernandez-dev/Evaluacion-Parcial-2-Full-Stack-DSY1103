package com.dsy1103.mspedidos.service;

import com.dsy1103.mspedidos.Client.InventarioClient;
import com.dsy1103.mspedidos.Client.ProductoClient;
import com.dsy1103.mspedidos.dto.DetallePedidoDTO;
import com.dsy1103.mspedidos.dto.InventarioDTO;
import com.dsy1103.mspedidos.dto.PedidoDTO;
import com.dsy1103.mspedidos.dto.ProductoDTO;
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
    @Autowired
    private ProductoClient productoClient;
    @Autowired
    private InventarioClient inventarioClient;

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
    public void actualizar(DetallePedidoDTO dDTO) {
        log.info("Actualizando DETALLE PEDIDO con ID: {}", dDTO.getId());

        // 1. Validamos que el detalle exista en nuestra BD local
        DetallePedidoModelo detalleExistente = detallePedidoRepo.findById(dDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Error: DETALLE PEDIDO no encontrado."));

        // 2. ADUANA 1: Llamamos a ms-productos (Puerto 8082 de tu compañero)
        try {
            ProductoDTO prod = productoClient.obtenerProductoPorId(dDTO.getProductoId());
            log.info("Producto validado correctamente en ms-productos: {}", prod.getNombreProducto());
        } catch (Exception e) {
            throw new EntityNotFoundException("Error: El producto con ID " + dDTO.getProductoId() + " no existe en el catálogo.");
        }

        // 3. ADUANA 2: Validamos el stock en ms-inventario (También en el puerto 8082 o el que use inventario)
        try {
            InventarioDTO inventario = inventarioClient.obtenerInventarioPorProductoId(dDTO.getProductoId());

            // Regla de negocio: Si el cliente pide más de lo que hay en bodega, rebotamos
            if (dDTO.getCantidadPedido() > inventario.getCantidadDisponible()) {
                throw new IllegalArgumentException("No hay suficiente stock disponible. En bodega quedan: " + inventario.getCantidadDisponible());
            }
            log.info("Stock verificado con éxito en ms-inventario. Disponible: {}", inventario.getCantidadDisponible());

        } catch (Exception e) {
            throw new EntityNotFoundException("Error de Inventario: " + e.getMessage());
        }

        // 4. Si pasó todas las aduanas, guardamos con el Builder tradicional
        detallePedidoRepo.save(DetallePedidoModelo.builder()
                .id(dDTO.getId())
                .productoId(dDTO.getProductoId())
                .cantidadPedido(dDTO.getCantidadPedido())
                .precioUnitario(dDTO.getPrecioUnitario())
                .subtotal(dDTO.getSubtotal())
                .observacion(dDTO.getObservacion())
                .fechaRegistro(dDTO.getFechaRegistro())
                .estadoDetalle(dDTO.getEstadoDetalle())
                .pedido(detalleExistente.getPedido())
                .build());
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
