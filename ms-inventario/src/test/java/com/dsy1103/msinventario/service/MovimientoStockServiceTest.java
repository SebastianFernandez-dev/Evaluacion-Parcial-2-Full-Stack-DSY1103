package com.dsy1103.msinventario.service;

import com.dsy1103.msinventario.dto.MovimientoStockDTO;
import com.dsy1103.msinventario.model.InventarioModel;
import com.dsy1103.msinventario.model.MovimientoStockModel;
import com.dsy1103.msinventario.repository.InventarioRepository;
import com.dsy1103.msinventario.repository.MovimientoStockRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class MovimientoStockServiceTest {

    @Mock
    private MovimientoStockRepository movimientoStockRepository;
    @Mock
    private InventarioRepository inventarioRepository;
    @InjectMocks
    private MovimientoStockService movimientoStockService;

    @Test
    @DisplayName("Debe guardar un movimiento de stock exitosamente si el inventario existe")
    void debeGuardarMovimientoExitosamente() {
        //Arrange
        MovimientoStockDTO dtoEntrada = MovimientoStockDTO.builder()
                .id(1L).inventarioId(10L).tipo("INGRESO").cantidad(5).build();

        InventarioModel inventarioMock = InventarioModel.builder()
                .id(10L).codigo("INV-10").build();

        MovimientoStockModel movimientoGuardadoMock = MovimientoStockModel.builder()
                .id(1L).tipo("INGRESO").cantidad(5).inventario(inventarioMock).build();

        Mockito.when(inventarioRepository.findById(10L)).thenReturn(Optional.of(inventarioMock));
        Mockito.when(movimientoStockRepository.save(any(MovimientoStockModel.class))).thenReturn(movimientoGuardadoMock);

        //Act
        MovimientoStockDTO resultado = movimientoStockService.guardarMovimiento(dtoEntrada);

        //Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("INGRESO", resultado.getTipo());
        //Verificamos que se llamo a guardar exactamente una vez
        Mockito.verify(movimientoStockRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("Debe lanzar EntityNotFoundException al guardar movimiento si el inventario asociado no existe")
    void debeLanzarExcepcionAlGuardarSiInventarioNoExiste() {
        //Arrange
        MovimientoStockDTO dtoEntrada = MovimientoStockDTO.builder()
                .id(1L).inventarioId(999L).build();

        Mockito.when(inventarioRepository.findById(999L)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            movimientoStockService.guardarMovimiento(dtoEntrada);
        });

        //Aseguramos que nunca se llamo a guardar el movimiento porque fallo antes
        Mockito.verify(movimientoStockRepository, Mockito.never()).save(any());
    }

    @Test
    @DisplayName("Debe eliminar un movimiento de stock si existe por su ID")
    void debeEliminarMovimientoExitosamente() {
        //Arrange
        Long idEliminar = 1L;
        Mockito.when(movimientoStockRepository.existsById(idEliminar)).thenReturn(true);

        //Act
        movimientoStockService.eliminarMovimiento(idEliminar);

        //Assert
        Mockito.verify(movimientoStockRepository, Mockito.times(1)).deleteById(idEliminar);
    }
}
