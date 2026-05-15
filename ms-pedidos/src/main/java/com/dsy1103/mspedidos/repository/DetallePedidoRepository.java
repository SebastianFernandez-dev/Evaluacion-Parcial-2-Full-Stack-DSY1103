package com.dsy1103.mspedidos.repository;

import com.dsy1103.mspedidos.modelo.DetallePedidoModelo;
import com.dsy1103.mspedidos.modelo.PedidoModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedidoModelo, Long>{

    List<DetallePedidoModelo> findByPedido_Id(Long pedidoId);

}
