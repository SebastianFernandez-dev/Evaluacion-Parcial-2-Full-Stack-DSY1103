package com.dsy1103.msempleados.mapper;

import com.dsy1103.msempleados.dto.request.EmpleadoRequestDTO;
import com.dsy1103.msempleados.dto.response.EmpleadoResponseDTO;
import com.dsy1103.msempleados.model.EmpleadoModel;
import org.springframework.stereotype.Component;

@Component
public class EmpleadoMapper {

    public EmpleadoResponseDTO toResponseDTO(EmpleadoModel eModel) {
        if(eModel == null) return null;

        return EmpleadoResponseDTO.builder()
                .id(eModel.getId())
                .primerNombre(eModel.getPrimerNombre())
                .segundoNombre(eModel.getSegundoNombre())
                .primerApellido(eModel.getPrimerApellido())
                .segundoApellido(eModel.getSegundoApellido())
                .cargo(eModel.getCargo())
                .rut(eModel.getRut())
                .dvRut(eModel.getDvRut())
                .correoEmpleado(eModel.getCorreoEmpleado())
                .fechaIngreso(eModel.getFechaIngreso())
                .activoEmpleado(eModel.getActivoEmpleado())
                .sucursalId(eModel.getSucursalId())
                .build();
    }

    public EmpleadoModel toEntity(EmpleadoRequestDTO eDTO) {
        if(eDTO == null) return null;

        return EmpleadoModel.builder()
                .primerNombre(eDTO.getPrimerNombre())
                .segundoNombre(eDTO.getSegundoNombre())
                .primerApellido(eDTO.getPrimerApellido())
                .segundoApellido(eDTO.getSegundoApellido())
                .cargo(eDTO.getCargo())
                .rut(eDTO.getRut())
                .dvRut(eDTO.getDvRut())
                .correoEmpleado(eDTO.getCorreoEmpleado())
                .fechaIngreso(eDTO.getFechaIngreso())
                .activoEmpleado(eDTO.getActivoEmpleado())
                .sucursalId(eDTO.getSucursalId())
                .build();
    }

    public void updateEntity(EmpleadoRequestDTO eDTO, EmpleadoModel eModel) {
        if(eDTO == null || eModel == null) return;

        eModel.setPrimerNombre(eDTO.getPrimerNombre());
        eModel.setSegundoNombre(eDTO.getSegundoNombre());
        eModel.setPrimerApellido(eDTO.getPrimerApellido());
        eModel.setSegundoApellido(eDTO.getSegundoApellido());
        eModel.setCargo(eDTO.getCargo());
        eModel.setRut(eDTO.getRut());
        eModel.setDvRut(eDTO.getDvRut());
        eModel.setCorreoEmpleado(eDTO.getCorreoEmpleado());
        eModel.setFechaIngreso(eDTO.getFechaIngreso());
        eModel.setActivoEmpleado(eDTO.getActivoEmpleado());
        eModel.setSucursalId(eDTO.getSucursalId());
    }
}
