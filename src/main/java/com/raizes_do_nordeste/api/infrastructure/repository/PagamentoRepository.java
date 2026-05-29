package com.raizes_do_nordeste.api.infrastructure.repository;

import com.raizes_do_nordeste.api.domain.entity.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
    // encontrar por id do pedido
    Optional<Pagamento> findByPedidoId(Long pedidoId);
}