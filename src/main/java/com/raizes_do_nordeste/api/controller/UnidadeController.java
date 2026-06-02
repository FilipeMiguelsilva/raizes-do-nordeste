package com.raizes_do_nordeste.api.controller;

import com.raizes_do_nordeste.api.application.service.UnidadeService;
import com.raizes_do_nordeste.api.domain.entity.Unidade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/unidades")
public class UnidadeController {

    private final UnidadeService unidadeService;

    public UnidadeController(UnidadeService unidadeService) {
        this.unidadeService = unidadeService;
    }

    @PostMapping
    public ResponseEntity<Unidade> cadastrar(@RequestBody Unidade unidade) {
        return ResponseEntity.status(201)
                .body(unidadeService.cadastrar(unidade));
    }

    @GetMapping
    public ResponseEntity<List<Unidade>> listarTodos() {
        return ResponseEntity.ok(unidadeService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Unidade> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(unidadeService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Unidade> atualizar(@PathVariable Long id,
                                             @RequestBody Unidade unidade) {
        return ResponseEntity.ok(unidadeService.atualizar(id, unidade));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        unidadeService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}