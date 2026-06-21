package com.dsy1103.mspagos.service;

import com.dsy1103.mspagos.client.PedidoClient;
import com.dsy1103.mspagos.dto.PagoDTO;
import com.dsy1103.mspagos.dto.PedidoDTO;
import com.dsy1103.mspagos.mapper.PagoMapper;
import com.dsy1103.mspagos.model.PagoModel;
import com.dsy1103.mspagos.repository.PagoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;
    @Mock
    private PagoMapper pagoMapper;
    @Mock
    private PedidoClient pedidoClient;
    @InjectMocks
    private PagoService pagoService;

    @Test
    @DisplayName("Debe listar todos los pagos exitosamente")
    void debeListarPagosExitosamente() {
        //Arrange
        PagoModel pago = PagoModel.builder()
                .id(1L).codigoTransaccion("TXN-001").monto(10000.0).build();
        PagoDTO pagoDTO = PagoDTO.builder()
                .id(1L).codigoTransaccion("TXN-001").monto(10000.0).build();

        Mockito.when(pagoRepository.findAll()).thenReturn(List.of(pago));
        Mockito.when(pagoMapper.toDTO(pago)).thenReturn(pagoDTO);

        //Act
        List<PagoDTO> resultado = pagoService.listarPagos();

        //Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("TXN-001", resultado.get(0).getCodigoTransaccion());
        assertEquals(10000.0, resultado.get(0).getMonto());
    }

    @Test
    @DisplayName("Debe obtener pago por ID exitosamente")
    void debeObtenerPagoPorIdExitosamente() {
        //Arrange
        Long pagoId = 1L;
        PagoModel pago = PagoModel.builder()
                .id(pagoId).codigoTransaccion("TXN-001").monto(10000.0).build();
        PagoDTO pagoDTO = PagoDTO.builder()
                .id(pagoId).codigoTransaccion("TXN-001").monto(10000.0).build();

        Mockito.when(pagoRepository.findById(pagoId)).thenReturn(Optional.of(pago));
        Mockito.when(pagoMapper.toDTO(pago)).thenReturn(pagoDTO);

        //Act
        PagoDTO resultado = pagoService.obtenerPagoPorId(pagoId);

        //Assert
        assertNotNull(resultado);
        assertEquals("TXN-001", resultado.getCodigoTransaccion());
        assertEquals(10000.0, resultado.getMonto());
    }

    @Test
    @DisplayName("Debe lanzar EntityNotFoundException cuando el pago no existe al buscar")
    void debeLanzarExcepcionCuandoPagoNoExisteAlBuscar() {
        //Arrange
        Mockito.when(pagoRepository.findById(1L)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            pagoService.obtenerPagoPorId(1L);
        });
    }

    @Test
    @DisplayName("Debe guardar un pago exitosamente")
    void debeGuardarPagoExitosamente() {
        //Arrange
        Long pedidoId = 1L;
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setId(pedidoId);

        PagoDTO dtoEntrada = PagoDTO.builder()
                .codigoTransaccion("TXN-NEW").pedidoId(pedidoId).monto(25000.0)
                .metodoPago("Transferencia").estadoPago("PENDIENTE")
                .fechaPago(LocalDate.of(2026, 6, 1)).activo(true).build();

        PagoModel modelParaGuardar = PagoModel.builder()
                .codigoTransaccion("TXN-NEW").pedidoId(pedidoId).monto(25000.0)
                .metodoPago("Transferencia").estadoPago("PENDIENTE")
                .fechaPago(LocalDate.of(2026, 6, 1)).activo(true).build();

        PagoModel modelGuardado = PagoModel.builder()
                .id(1L).codigoTransaccion("TXN-NEW").pedidoId(pedidoId).monto(25000.0)
                .metodoPago("Transferencia").estadoPago("PENDIENTE")
                .fechaPago(LocalDate.of(2026, 6, 1)).activo(true).build();

        PagoDTO dtoSalida = PagoDTO.builder()
                .id(1L).codigoTransaccion("TXN-NEW").pedidoId(pedidoId).monto(25000.0)
                .metodoPago("Transferencia").estadoPago("PENDIENTE")
                .fechaPago(LocalDate.of(2026, 6, 1)).activo(true).build();

        Mockito.when(pedidoClient.obtenerPedidoPorId(pedidoId)).thenReturn(pedidoDTO);
        Mockito.when(pagoMapper.toEntity(dtoEntrada)).thenReturn(modelParaGuardar);
        Mockito.when(pagoRepository.save(modelParaGuardar)).thenReturn(modelGuardado);
        Mockito.when(pagoMapper.toDTO(modelGuardado)).thenReturn(dtoSalida);

        //Act
        PagoDTO resultado = pagoService.guardarPago(dtoEntrada);

        //Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("TXN-NEW", resultado.getCodigoTransaccion());
        assertEquals(pedidoId, resultado.getPedidoId());
        Mockito.verify(pagoRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("Debe actualizar un pago exitosamente")
    void debeActualizarPagoExitosamente() {
        //Arrange
        Long pagoId = 1L;
        Long pedidoId = 1L;
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setId(pedidoId);

        PagoModel existente = PagoModel.builder()
                .id(pagoId).codigoTransaccion("TXN-001").pedidoId(pedidoId).monto(10000.0)
                .metodoPago("Tarjeta").estadoPago("APROBADO")
                .fechaPago(LocalDate.of(2026, 1, 10)).activo(true).build();

        PagoDTO dtoActualizacion = PagoDTO.builder()
                .id(pagoId).codigoTransaccion("TXN-001-UPD").pedidoId(pedidoId).monto(15000.0)
                .metodoPago("Débito").estadoPago("APROBADO")
                .fechaPago(LocalDate.of(2026, 6, 15)).activo(true).build();

        PagoModel modelActualizado = PagoModel.builder()
                .id(pagoId).codigoTransaccion("TXN-001-UPD").pedidoId(pedidoId).monto(15000.0)
                .metodoPago("Débito").estadoPago("APROBADO")
                .fechaPago(LocalDate.of(2026, 6, 15)).activo(true).build();

        PagoDTO dtoSalida = PagoDTO.builder()
                .id(pagoId).codigoTransaccion("TXN-001-UPD").pedidoId(pedidoId).monto(15000.0)
                .metodoPago("Débito").estadoPago("APROBADO")
                .fechaPago(LocalDate.of(2026, 6, 15)).activo(true).build();

        Mockito.when(pagoRepository.findById(pagoId)).thenReturn(Optional.of(existente));
        Mockito.when(pedidoClient.obtenerPedidoPorId(pedidoId)).thenReturn(pedidoDTO);
        Mockito.when(pagoRepository.save(existente)).thenReturn(modelActualizado);
        Mockito.when(pagoMapper.toDTO(modelActualizado)).thenReturn(dtoSalida);

        //Act
        PagoDTO resultado = pagoService.actualizarPago(dtoActualizacion);

        //Assert
        assertNotNull(resultado);
        assertEquals("TXN-001-UPD", resultado.getCodigoTransaccion());
        assertEquals(15000.0, resultado.getMonto());
    }

    @Test
    @DisplayName("Debe lanzar EntityNotFoundException al actualizar un pago inexistente")
    void debeLanzarExcepcionAlActualizarPagoInexistente() {
        //Arrange
        PagoDTO dtoActualizacion = PagoDTO.builder().id(999L).build();
        Mockito.when(pagoRepository.findById(999L)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            pagoService.actualizarPago(dtoActualizacion);
        });
    }

    @Test
    @DisplayName("Debe lanzar EntityNotFoundException al intentar eliminar un pago inexistente")
    void debeLanzarExcepcionAlEliminarPagoInexistente() {
        //Arrange
        Mockito.when(pagoRepository.existsById(1L)).thenReturn(false);

        //Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            pagoService.eliminarPago(1L);
        });

        Mockito.verify(pagoRepository, Mockito.never()).deleteById(any());
    }

    @Test
    @DisplayName("Debe eliminar un pago exitosamente si existe")
    void debeEliminarPagoExitosamente() {
        //Arrange
        Long idEliminar = 1L;
        Mockito.when(pagoRepository.existsById(idEliminar)).thenReturn(true);

        //Act
        pagoService.eliminarPago(idEliminar);

        //Assert
        Mockito.verify(pagoRepository, Mockito.times(1)).deleteById(idEliminar);
    }
}
