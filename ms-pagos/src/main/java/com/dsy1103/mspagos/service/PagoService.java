package com.dsy1103.mspagos.service;

import com.dsy1103.mspagos.client.PedidoClient;
import com.dsy1103.mspagos.dto.PagoDTO;
import com.dsy1103.mspagos.mapper.PagoMapper;
import com.dsy1103.mspagos.model.PagoModel;
import com.dsy1103.mspagos.repository.PagoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;
    @Autowired
    private PagoMapper pagoMapper;
    @Autowired
    private PedidoClient pedidoClient;

    public List<PagoDTO> listarPagos() {
        log.info("Listando todos los PAGOS");
        return pagoRepository.findAll()
                .stream()
                .map(pagoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PagoDTO obtenerPagoPorId(Long id) {
        log.info("Obteniendo PAGO por ID {}", id);
        return pagoRepository.findById(id)
                .map(pagoMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Error: El PAGO con ID " + id + " no pudo ser encontrado"));
    }

    public List<PagoDTO> buscarPorMontoYEstado(Double monto, String estadoPago) {
        log.info("Buscando PAGOS con monto mayor a {} y estado '{}'", monto, estadoPago);
        return pagoRepository.findByMontoGreaterThanAndEstadoPago(monto, estadoPago)
                .stream()
                .map(pagoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PagoDTO guardarPago(PagoDTO dto) {
        log.info("Registrando PAGO con codigoTransaccion: {}", dto.getCodigoTransaccion());

        pedidoClient.obtenerPedidoPorId(dto.getPedidoId());

        PagoModel model = pagoMapper.toEntity(dto);
        PagoModel guardado = pagoRepository.save(model);
        log.info("PAGO guardado exitosamente con ID: {}", guardado.getId());
        return pagoMapper.toDTO(guardado);
    }

    public PagoDTO actualizarPago(PagoDTO dto) {
        log.info("Actualizando PAGO con ID {}", dto.getId());

        PagoModel existente = pagoRepository.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Error: PAGO no encontrado"));

        pedidoClient.obtenerPedidoPorId(dto.getPedidoId());

        existente.setCodigoTransaccion(dto.getCodigoTransaccion());
        existente.setPedidoId(dto.getPedidoId());
        existente.setMonto(dto.getMonto());
        existente.setMetodoPago(dto.getMetodoPago());
        existente.setEstadoPago(dto.getEstadoPago());
        existente.setFechaPago(dto.getFechaPago());
        existente.setActivo(dto.getActivo());

        PagoModel actualizado = pagoRepository.save(existente);
        return pagoMapper.toDTO(actualizado);
    }

    public void eliminarPago(Long id) {
        log.warn("Eliminando PAGO con ID: {}", id);
        if (!pagoRepository.existsById(id)) {
            throw new EntityNotFoundException("Error: PAGO no encontrado");
        }
        pagoRepository.deleteById(id);
        log.info("PAGO eliminado exitosamente con ID: {}", id);
    }

}
