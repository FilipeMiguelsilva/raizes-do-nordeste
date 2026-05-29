package com.raizes_do_nordeste.api.infrastructure.repository;

import com.raizes_do_nordeste.api.domain.entity.ItensPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ItensPedidoRepository extends JpaRepository<ItensPedido, Long> {
    // encontrar por id do pedido
    List<ItensPedido> findByPedidoId(Long pedidoId);
}