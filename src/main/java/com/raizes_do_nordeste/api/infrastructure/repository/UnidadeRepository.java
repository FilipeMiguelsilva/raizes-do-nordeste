package com.raizes_do_nordeste.api.infrastructure.repository;

import com.raizes_do_nordeste.api.domain.entity.Produto;
import com.raizes_do_nordeste.api.domain.entity.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UnidadeRepository extends JpaRepository<Unidade, Long> {
    // encontrar por nome e status
    List<Unidade> findByStatus(Boolean status);

    Optional<Produto> findByNome(String nome);
}