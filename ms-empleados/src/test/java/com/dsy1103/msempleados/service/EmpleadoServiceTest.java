package com.dsy1103.msempleados.service;

import com.dsy1103.msempleados.client.SucursalClient;
import com.dsy1103.msempleados.dto.request.EmpleadoRequestDTO;
import com.dsy1103.msempleados.dto.response.EmpleadoResponseDTO;
import com.dsy1103.msempleados.dto.SucursalDTO;
import com.dsy1103.msempleados.mapper.EmpleadoMapper;
import com.dsy1103.msempleados.model.EmpleadoModel;
import com.dsy1103.msempleados.repository.EmpleadoRepository;
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
class EmpleadoServiceTest {

    @Mock
    private EmpleadoRepository empleadoRepository;
    @Mock
    private EmpleadoMapper empleadoMapper;
    @Mock
    private SucursalClient sucursalClient;
    @InjectMocks
    private EmpleadoService empleadoService;

    @Test
    @DisplayName("Debe listar todos los empleados exitosamente")
    void debeListarEmpleadosExitosamente() {
        EmpleadoModel empleado = EmpleadoModel.builder()
                .id(1L).primerNombre("Juan").primerApellido("Perez").build();
        EmpleadoResponseDTO dto = EmpleadoResponseDTO.builder()
                .id(1L).primerNombre("Juan").primerApellido("Perez").build();

        Mockito.when(empleadoRepository.findAll()).thenReturn(List.of(empleado));
        Mockito.when(empleadoMapper.toResponseDTO(empleado)).thenReturn(dto);

        List<EmpleadoResponseDTO> resultado = empleadoService.listarEmpleados();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Juan", resultado.get(0).getPrimerNombre());
        assertEquals("Perez", resultado.get(0).getPrimerApellido());
    }

    @Test
    @DisplayName("Debe obtener empleado por ID exitosamente")
    void debeObtenerEmpleadoPorIdExitosamente() {
        Long empleadoId = 1L;
        EmpleadoModel empleado = EmpleadoModel.builder()
                .id(empleadoId).primerNombre("Maria").build();
        EmpleadoResponseDTO dto = EmpleadoResponseDTO.builder()
                .id(empleadoId).primerNombre("Maria").build();

        Mockito.when(empleadoRepository.findById(empleadoId)).thenReturn(Optional.of(empleado));
        Mockito.when(empleadoMapper.toResponseDTO(empleado)).thenReturn(dto);

        EmpleadoResponseDTO resultado = empleadoService.obtenerEmpleadoPorID(empleadoId);

        assertNotNull(resultado);
        assertEquals("Maria", resultado.getPrimerNombre());
    }

    @Test
    @DisplayName("Debe lanzar EntityNotFoundException cuando el empleado no existe al buscar")
    void debeLanzarExcepcionCuandoEmpleadoNoExiste() {
        Mockito.when(empleadoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            empleadoService.obtenerEmpleadoPorID(1L);
        });
    }

    @Test
    @DisplayName("Debe guardar un empleado exitosamente")
    void debeGuardarEmpleadoExitosamente() {
        Long sucursalId = 1L;
        EmpleadoRequestDTO dtoEntrada = EmpleadoRequestDTO.builder()
                .primerNombre("Carlos").primerApellido("Lopez")
                .rut(12345678).dvRut("K").correoEmpleado("carlos@test.com")
                .fechaIngreso(LocalDate.now()).activoEmpleado(true).sucursalId(sucursalId).build();

        EmpleadoModel modelParaGuardar = EmpleadoModel.builder()
                .primerNombre("Carlos").primerApellido("Lopez")
                .rut(12345678).dvRut("K").correoEmpleado("carlos@test.com")
                .fechaIngreso(LocalDate.now()).activoEmpleado(true).sucursalId(sucursalId).build();

        EmpleadoModel modelGuardado = EmpleadoModel.builder()
                .id(1L).primerNombre("Carlos").primerApellido("Lopez")
                .rut(12345678).dvRut("K").correoEmpleado("carlos@test.com")
                .fechaIngreso(LocalDate.now()).activoEmpleado(true).sucursalId(sucursalId).build();

        EmpleadoResponseDTO dtoSalida = EmpleadoResponseDTO.builder()
                .id(1L).primerNombre("Carlos").primerApellido("Lopez").build();

        Mockito.when(sucursalClient.obtenerSucursalPorId(sucursalId)).thenReturn(new SucursalDTO());
        Mockito.when(empleadoMapper.toEntity(dtoEntrada)).thenReturn(modelParaGuardar);
        Mockito.when(empleadoRepository.save(modelParaGuardar)).thenReturn(modelGuardado);
        Mockito.when(empleadoMapper.toResponseDTO(modelGuardado)).thenReturn(dtoSalida);

        EmpleadoResponseDTO resultado = empleadoService.guardarEmpleado(dtoEntrada);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Carlos", resultado.getPrimerNombre());
        Mockito.verify(empleadoRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("Debe actualizar un empleado exitosamente")
    void debeActualizarEmpleadoExitosamente() {
        Long empleadoId = 1L;
        Long sucursalId = 1L;
        EmpleadoModel existente = EmpleadoModel.builder()
                .id(empleadoId).primerNombre("Viejo").primerApellido("Nombre").sucursalId(sucursalId).build();

        EmpleadoRequestDTO dtoActualizacion = EmpleadoRequestDTO.builder()
                .primerNombre("Nuevo").primerApellido("Nombre")
                .rut(87654321).dvRut("5").correoEmpleado("nuevo@test.com")
                .fechaIngreso(LocalDate.now()).activoEmpleado(true).sucursalId(sucursalId).build();

        EmpleadoResponseDTO dtoSalida = EmpleadoResponseDTO.builder()
                .id(empleadoId).primerNombre("Nuevo").primerApellido("Nombre").build();

        Mockito.when(empleadoRepository.findById(empleadoId)).thenReturn(Optional.of(existente));
        Mockito.when(sucursalClient.obtenerSucursalPorId(sucursalId)).thenReturn(new SucursalDTO());
        Mockito.when(empleadoRepository.save(existente)).thenReturn(existente);
        Mockito.when(empleadoMapper.toResponseDTO(existente)).thenReturn(dtoSalida);

        EmpleadoResponseDTO resultado = empleadoService.actualizarEmpleado(empleadoId, dtoActualizacion);

        assertNotNull(resultado);
        assertEquals("Nuevo", resultado.getPrimerNombre());
    }

    @Test
    @DisplayName("Debe lanzar EntityNotFoundException al actualizar un empleado inexistente")
    void debeLanzarExcepcionAlActualizarEmpleadoInexistente() {
        EmpleadoRequestDTO dtoActualizacion = EmpleadoRequestDTO.builder()
                .primerNombre("Test").primerApellido("Test").rut(11111111).dvRut("1")
                .correoEmpleado("test@test.com").fechaIngreso(LocalDate.now())
                .activoEmpleado(true).sucursalId(1L).build();

        Mockito.when(empleadoRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            empleadoService.actualizarEmpleado(999L, dtoActualizacion);
        });
    }

    @Test
    @DisplayName("Debe lanzar EntityNotFoundException al intentar eliminar un empleado inexistente")
    void debeLanzarExcepcionAlEliminarEmpleadoInexistente() {
        Mockito.when(empleadoRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> {
            empleadoService.eliminarEmpleado(1L);
        });

        Mockito.verify(empleadoRepository, Mockito.never()).deleteById(any());
    }

    @Test
    @DisplayName("Debe eliminar un empleado exitosamente si existe")
    void debeEliminarEmpleadoExitosamente() {
        Long idEliminar = 1L;
        Mockito.when(empleadoRepository.existsById(idEliminar)).thenReturn(true);

        empleadoService.eliminarEmpleado(idEliminar);

        Mockito.verify(empleadoRepository, Mockito.times(1)).deleteById(idEliminar);
    }

    @Test
    @DisplayName("Debe listar empleados por sucursal y año exitosamente")
    void debeListarPorSucursalYAnioExitosamente() {
        Long sucursalId = 1L;
        int anio = 2024;
        EmpleadoModel empleado = EmpleadoModel.builder()
                .id(1L).primerNombre("Ana").sucursalId(sucursalId).build();
        EmpleadoResponseDTO dto = EmpleadoResponseDTO.builder()
                .id(1L).primerNombre("Ana").sucursalId(sucursalId).build();

        Mockito.when(sucursalClient.obtenerSucursalPorId(sucursalId)).thenReturn(new SucursalDTO());
        Mockito.when(empleadoRepository.findAllBySucursalAndAnio(sucursalId, anio)).thenReturn(List.of(empleado));
        Mockito.when(empleadoMapper.toResponseDTO(empleado)).thenReturn(dto);

        List<EmpleadoResponseDTO> resultado = empleadoService.listarPorSucursalYAnio(sucursalId, anio);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Ana", resultado.get(0).getPrimerNombre());
    }
}
