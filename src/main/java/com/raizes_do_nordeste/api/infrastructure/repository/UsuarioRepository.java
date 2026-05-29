package com.raizes_do_nordeste.api.infrastructure.repository;

import com.raizes_do_nordeste.api.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
        //encontrar por email
        Optional<Usuario> findByEmail(String email);
}

