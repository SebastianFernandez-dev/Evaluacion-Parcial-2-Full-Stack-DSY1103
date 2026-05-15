package com.dsy1103.msempleados.service;

import com.dsy1103.msempleados.dto.EmpleadoDTO;
import com.dsy1103.msempleados.mapper.EmpleadoMapper;
import com.dsy1103.msempleados.repository.EmpleadoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;
    @Autowired
    private EmpleadoMapper empleadoMapper;


}
