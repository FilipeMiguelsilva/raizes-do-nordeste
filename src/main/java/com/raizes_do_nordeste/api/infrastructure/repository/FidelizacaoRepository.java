package com.raizes_do_nordeste.api.infrastructure.repository;

import com.raizes_do_nordeste.api.domain.entity.Fidelizacao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FidelizacaoRepository extends JpaRepository<Fidelizacao, Long> {
    // encontrar por id do usuario
    Optional<Fidelizacao> findByUsuarioId(Long usuarioId);
}