package com.dsy1103.msempleados.mapper;

import com.dsy1103.msempleados.dto.EmpleadoDTO;
import com.dsy1103.msempleados.model.EmpleadoModel;
import org.springframework.stereotype.Component;

@Component
public class EmpleadoMapper {

    public EmpleadoDTO toDTO(EmpleadoModel eModel) {
        if(eModel == null) return null;

        return EmpleadoDTO.builder()
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

    public EmpleadoModel toEntity(EmpleadoDTO eDTO) {
        if(eDTO == null) return null;

        return EmpleadoModel.builder()
                .id(eDTO.getId())
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
}
