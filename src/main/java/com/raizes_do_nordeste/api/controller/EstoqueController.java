package com.raizes_do_nordeste.api.controller;

import com.raizes_do_nordeste.api.application.service.EstoqueService;
import com.raizes_do_nordeste.api.domain.entity.Estoque;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {

    private final EstoqueService estoqueService;

    public EstoqueController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @PostMapping
    public ResponseEntity<Estoque> cadastrar(@RequestBody Estoque estoque) {
        return ResponseEntity.status(201)
                .body(estoqueService.cadastrar(estoque));
    }

    @GetMapping
    public ResponseEntity<List<Estoque>> listarTodos() {
        return ResponseEntity.ok(estoqueService.listarTodos());
    }

    @GetMapping("/unidade/{unidadeId}")
    public ResponseEntity<List<Estoque>> listarPorUnidade(@PathVariable Long unidadeId) {
        return ResponseEntity.ok(estoqueService.listarPorUnidade(unidadeId));
    }

    @PatchMapping("/{id}/adicionar")
    public ResponseEntity<Estoque> adicionarQuantidade(@PathVariable Long id,
                                                       @RequestParam int quantidade) {

        return ResponseEntity.ok(
                estoqueService.adicionarQuantidade(id, quantidade)
        );
    }

    @PatchMapping("/remover")
    public ResponseEntity<Estoque> removerQuantidade(
            @RequestParam Long unidadeId,
            @RequestParam Long produtoId,
            @RequestParam int quantidade) {

        return ResponseEntity.ok(
                estoqueService.removerPorUnidadeEProduto(
                        unidadeId,
                        produtoId,
                        quantidade
                )
        );
    }
}