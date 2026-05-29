package com.raizes_do_nordeste.api.infrastructure.repository;

import com.raizes_do_nordeste.api.domain.entity.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
    List<Estoque> findByUnidadeId(Long unidadeId);
    Optional<Estoque> findByUnidadeIdAndProdutoId(Long unidadeId, Long produtoId);
}