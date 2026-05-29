package com.raizes_do_nordeste.api.controller;

import com.raizes_do_nordeste.api.application.service.UsuarioService;
import com.raizes_do_nordeste.api.domain.entity.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario) {
        return ResponseEntity.status(201).body(usuarioService.cadastrar(usuario));
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id,
                                             @RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.atualizar(id, usuario));
    }

    @PatchMapping("/{id}/ativar")
    public ResponseEntity<Usuario> ativar(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.ativar(id));
    }

    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Usuario> desativar(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.desativar(id));
    }
}