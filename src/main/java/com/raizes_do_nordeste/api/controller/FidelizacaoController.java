package com.raizes_do_nordeste.api.controller;

import com.raizes_do_nordeste.api.application.service.FidelizacaoService;
import com.raizes_do_nordeste.api.domain.entity.Fidelizacao;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fidelizacao")
public class FidelizacaoController {

    private final FidelizacaoService fidelizacaoService;

    public FidelizacaoController(FidelizacaoService fidelizacaoService) {
        this.fidelizacaoService = fidelizacaoService;
    }

    @PostMapping
    public ResponseEntity<Fidelizacao> cadastrar(
            @RequestBody Fidelizacao fidelizacao) {

        return ResponseEntity.status(201)
                .body(fidelizacaoService.cadastrar(fidelizacao));
    }
}