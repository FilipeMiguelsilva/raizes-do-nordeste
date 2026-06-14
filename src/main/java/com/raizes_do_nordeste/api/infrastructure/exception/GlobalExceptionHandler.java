package com.raizes_do_nordeste.api.infrastructure.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAcessoNegado(AccessDeniedException ex) {
        return ResponseEntity.status(403).body(Map.of(
                "error", "ACESSO_NEGADO",
                "message", "Voce nao tem permissao para acessar este recurso."
        ));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex) {

        String mensagem = ex.getMessage();

        if (mensagem != null && mensagem.contains("insuficiente")) {
            return ResponseEntity.status(409).body(Map.of(
                    "error", "ESTOQUE_INSUFICIENTE",
                    "message", mensagem
            ));
        }

        if (mensagem != null && (mensagem.contains("nao encontrado") || mensagem.contains("Nao encontrado") || mensagem.contains("não encontrado"))) {
            return ResponseEntity.status(404).body(Map.of(
                    "error", "NAO_ENCONTRADO",
                    "message", mensagem
            ));
        }

        return ResponseEntity.status(400).body(Map.of(
                "error", "Erro na requisicao",
                "message", mensagem
        ));
    }
}