package com.raizes_do_nordeste.api.infrastructure.repository;

import com.raizes_do_nordeste.api.domain.entity.Pedido;
import com.raizes_do_nordeste.api.domain.enums.CanalPedido;
import com.raizes_do_nordeste.api.domain.enums.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    // encontrar por status tanto canal ou status pedido
    List<Pedido> findByStatus(StatusPedido status);
    List<Pedido> findByCanalPedido(CanalPedido canalPedido);
}