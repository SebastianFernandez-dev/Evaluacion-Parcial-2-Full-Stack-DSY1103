package com.dsy1103.mspedidos.repository;

import com.dsy1103.mspedidos.modelo.PedidoModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PedidoRepository extends JpaRepository<PedidoModelo, Long> {


    @Query("SELECT p FROM PedidoModelo p WHERE p.pagadopedido = true ORDER BY p.fechaPedido DESC")
    List<PedidoModelo> findPedidosPagadosOrdenadosPorFecha();

    List<PedidoModelo> findByCodigoPedidoAndPagadopedido(String codigoPedido, Boolean pagadopedido);

    // Buscar todos los pedidos que estén activos (si usas ese campo)
    List<PedidoModelo> findByPagadopedidoTrue();

    // Para buscar todos los pedidos de un usuario específico por su ID
    List<PedidoModelo> findByUsuarioId(Long usuarioId);


}
