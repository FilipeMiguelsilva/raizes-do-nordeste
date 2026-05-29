package com.raizes_do_nordeste.api.infrastructure.repository;

import com.raizes_do_nordeste.api.domain.entity.Cardapio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CardapioRepository extends JpaRepository<Cardapio, Long> {
    // encontrar por id da unidade
    List<Cardapio> findByUnidadeId(Long unidadeId);

    Optional<Cardapio> findByUnidadeIdAndProdutoId(Long unidadeId, Long produtoId);
}