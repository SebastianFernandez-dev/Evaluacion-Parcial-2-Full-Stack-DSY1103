package com.dsy1103.msproveedores.service;

import com.dsy1103.msproveedores.dto.ContratoRequestDTO;
import com.dsy1103.msproveedores.dto.ContratoResponseDTO;
import com.dsy1103.msproveedores.mapper.ContratoMapper;
import com.dsy1103.msproveedores.model.ContratoModel;
import com.dsy1103.msproveedores.model.ProveedorModel;
import com.dsy1103.msproveedores.repository.ContratoRepository;
import com.dsy1103.msproveedores.repository.ProveedorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ContratoServiceTest {

    @Mock
    private ContratoRepository contratoRepository;
    @Mock
    private ProveedorRepository proveedorRepository;
    @Mock
    private ContratoMapper contratoMapper;
    @InjectMocks
    private ContratoService contratoService;

    @Test
    @DisplayName("Debe listar todos los contratos exitosamente")
    void debeListarContratosExitosamente() {
        ProveedorModel proveedor = ProveedorModel.builder().id(1L).build();
        ContratoModel contrato = ContratoModel.builder()
                .id(1L).numero("CON-001").proveedor(proveedor).build();
        ContratoResponseDTO dto = ContratoResponseDTO.builder()
                .id(1L).numero("CON-001").proveedorId(1L).build();

        Mockito.when(contratoRepository.findAll()).thenReturn(List.of(contrato));
        Mockito.when(contratoMapper.toResponseDTO(contrato)).thenReturn(dto);

        List<ContratoResponseDTO> resultado = contratoService.listarContratos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("CON-001", resultado.get(0).getNumero());
    }

    @Test
    @DisplayName("Debe obtener contrato por ID exitosamente")
    void debeObtenerContratoPorIdExitosamente() {
        Long contratoId = 1L;
        ProveedorModel proveedor = ProveedorModel.builder().id(1L).build();
        ContratoModel contrato = ContratoModel.builder()
                .id(contratoId).numero("CON-001").proveedor(proveedor).build();
        ContratoResponseDTO dto = ContratoResponseDTO.builder()
                .id(contratoId).numero("CON-001").proveedorId(1L).build();

        Mockito.when(contratoRepository.findById(contratoId)).thenReturn(Optional.of(contrato));
        Mockito.when(contratoMapper.toResponseDTO(contrato)).thenReturn(dto);

        ContratoResponseDTO resultado = contratoService.obtenerContratoPorId(contratoId);

        assertNotNull(resultado);
        assertEquals("CON-001", resultado.getNumero());
    }

    @Test
    @DisplayName("Debe lanzar EntityNotFoundException cuando el contrato no existe al buscar")
    void debeLanzarExcepcionCuandoContratoNoExiste() {
        Mockito.when(contratoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            contratoService.obtenerContratoPorId(1L);
        });
    }

    @Test
    @DisplayName("Debe listar contratos por proveedor exitosamente")
    void debeListarContratosPorProveedor() {
        Long proveedorId = 1L;
        ProveedorModel proveedor = ProveedorModel.builder().id(proveedorId).build();
        ContratoModel contrato = ContratoModel.builder()
                .id(1L).numero("CON-001").proveedor(proveedor).build();
        ContratoResponseDTO dto = ContratoResponseDTO.builder()
                .id(1L).numero("CON-001").proveedorId(proveedorId).build();

        Mockito.when(contratoRepository.findByProveedorId(proveedorId)).thenReturn(List.of(contrato));
        Mockito.when(contratoMapper.toResponseDTO(contrato)).thenReturn(dto);

        List<ContratoResponseDTO> resultado = contratoService.listarContratosPorProveedor(proveedorId);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(proveedorId, resultado.get(0).getProveedorId());
    }

    @Test
    @DisplayName("Debe guardar un contrato exitosamente si el proveedor existe")
    void debeGuardarContratoExitosamente() {
        Long proveedorId = 1L;
        ProveedorModel proveedor = ProveedorModel.builder().id(proveedorId).build();

        ContratoRequestDTO dtoEntrada = ContratoRequestDTO.builder()
                .numero("CON-NEW-001").tipo("Suministros").valor(50.0)
                .proveedorId(proveedorId).build();

        ContratoModel modelParaGuardar = ContratoModel.builder()
                .numero("CON-NEW-001").tipo("Suministros").valor(50.0).build();

        ContratoModel modelGuardado = ContratoModel.builder()
                .id(1L).numero("CON-NEW-001").tipo("Suministros").valor(50.0).proveedor(proveedor).build();

        ContratoResponseDTO dtoSalida = ContratoResponseDTO.builder()
                .id(1L).numero("CON-NEW-001").proveedorId(proveedorId).build();

        Mockito.when(contratoMapper.toEntity(dtoEntrada)).thenReturn(modelParaGuardar);
        Mockito.when(proveedorRepository.findById(proveedorId)).thenReturn(Optional.of(proveedor));
        Mockito.when(contratoRepository.save(modelParaGuardar)).thenReturn(modelGuardado);
        Mockito.when(contratoMapper.toResponseDTO(modelGuardado)).thenReturn(dtoSalida);

        ContratoResponseDTO resultado = contratoService.guardarContrato(dtoEntrada);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("CON-NEW-001", resultado.getNumero());
        Mockito.verify(contratoRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("Debe lanzar EntityNotFoundException al guardar contrato si el proveedor no existe")
    void debeLanzarExcepcionAlGuardarSiProveedorNoExiste() {
        ContratoRequestDTO dtoEntrada = ContratoRequestDTO.builder()
                .numero("CON-NEW-001").proveedorId(999L).build();

        Mockito.when(contratoMapper.toEntity(dtoEntrada)).thenReturn(ContratoModel.builder().build());
        Mockito.when(proveedorRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            contratoService.guardarContrato(dtoEntrada);
        });

        Mockito.verify(contratoRepository, Mockito.never()).save(any());
    }

    @Test
    @DisplayName("Debe actualizar un contrato exitosamente")
    void debeActualizarContratoExitosamente() {
        Long contratoId = 1L;
        Long proveedorId = 1L;
        ProveedorModel proveedor = ProveedorModel.builder().id(proveedorId).build();
        ContratoModel existente = ContratoModel.builder()
                .id(contratoId).numero("CON-001").proveedor(proveedor).build();

        ContratoRequestDTO dtoActualizacion = ContratoRequestDTO.builder()
                .numero("CON-UPD-001").tipo("Servicios").valor(100.0)
                .proveedorId(proveedorId).build();

        ContratoModel actualizado = ContratoModel.builder()
                .id(contratoId).numero("CON-UPD-001").tipo("Servicios").valor(100.0).proveedor(proveedor).build();

        ContratoResponseDTO dtoSalida = ContratoResponseDTO.builder()
                .id(contratoId).numero("CON-UPD-001").proveedorId(proveedorId).build();

        Mockito.when(contratoRepository.findById(contratoId)).thenReturn(Optional.of(existente));
        Mockito.when(proveedorRepository.findById(proveedorId)).thenReturn(Optional.of(proveedor));
        Mockito.when(contratoRepository.save(existente)).thenReturn(actualizado);
        Mockito.when(contratoMapper.toResponseDTO(actualizado)).thenReturn(dtoSalida);

        ContratoResponseDTO resultado = contratoService.actualizarContrato(contratoId, dtoActualizacion);

        assertNotNull(resultado);
        assertEquals("CON-UPD-001", resultado.getNumero());
        assertEquals(proveedorId, resultado.getProveedorId());
    }

    @Test
    @DisplayName("Debe lanzar EntityNotFoundException al actualizar un contrato inexistente")
    void debeLanzarExcepcionAlActualizarContratoInexistente() {
        ContratoRequestDTO dtoActualizacion = ContratoRequestDTO.builder()
                .numero("CON-001").proveedorId(1L).build();

        Mockito.when(contratoRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            contratoService.actualizarContrato(999L, dtoActualizacion);
        });
    }

    @Test
    @DisplayName("Debe lanzar EntityNotFoundException al intentar eliminar un contrato inexistente")
    void debeLanzarExcepcionAlEliminarContratoInexistente() {
        Mockito.when(contratoRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> {
            contratoService.eliminarContrato(1L);
        });

        Mockito.verify(contratoRepository, Mockito.never()).deleteById(any());
    }

    @Test
    @DisplayName("Debe eliminar un contrato exitosamente si existe")
    void debeEliminarContratoExitosamente() {
        Long idEliminar = 1L;
        Mockito.when(contratoRepository.existsById(idEliminar)).thenReturn(true);

        contratoService.eliminarContrato(idEliminar);

        Mockito.verify(contratoRepository, Mockito.times(1)).deleteById(idEliminar);
    }
}
