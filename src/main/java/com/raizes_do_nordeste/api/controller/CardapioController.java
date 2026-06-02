package com.raizes_do_nordeste.api.controller;

import com.raizes_do_nordeste.api.application.service.CardapioService;
import com.raizes_do_nordeste.api.domain.entity.Cardapio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cardapios")
public class CardapioController {

    private final CardapioService cardapioService;

    public CardapioController(CardapioService cardapioService) {
        this.cardapioService = cardapioService;
    }

    @PostMapping
    public ResponseEntity<Cardapio> cadastrar(@RequestBody Cardapio cardapio) {
        return ResponseEntity.status(201)
                .body(cardapioService.cadastrar(cardapio));
    }

    @GetMapping
    public ResponseEntity<List<Cardapio>> listarTodos() {
        return ResponseEntity.ok(cardapioService.listarTodos());
    }

    @GetMapping("/unidade/{unidadeId}")
    public ResponseEntity<List<Cardapio>> listarPorUnidade(@PathVariable Long unidadeId) {
        return ResponseEntity.ok(cardapioService.listarPorUnidade(unidadeId));
    }

    @PatchMapping("/{id}/ativar")
    public ResponseEntity<Cardapio> ativar(@PathVariable Long id) {
        return ResponseEntity.ok(cardapioService.ativar(id));
    }

    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Cardapio> desativar(@PathVariable Long id) {
        return ResponseEntity.ok(cardapioService.desativar(id));
    }
}