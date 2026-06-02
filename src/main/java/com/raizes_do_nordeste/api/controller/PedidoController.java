package com.raizes_do_nordeste.api.controller;

import com.raizes_do_nordeste.api.application.service.PedidoService;
import com.raizes_do_nordeste.api.controller.dto.PedidoRequest;
import com.raizes_do_nordeste.api.domain.entity.*;
import com.raizes_do_nordeste.api.domain.enums.CanalPedido;
import com.raizes_do_nordeste.api.domain.enums.StatusPedido;
import com.raizes_do_nordeste.api.infrastructure.repository.CardapioRepository;
import com.raizes_do_nordeste.api.infrastructure.repository.UnidadeRepository;
import com.raizes_do_nordeste.api.infrastructure.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.raizes_do_nordeste.api.application.service.PagamentoService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;
    private final PagamentoService pagamentoService;
    private final UsuarioRepository usuarioRepository;
    private final UnidadeRepository unidadeRepository;
    private final CardapioRepository cardapioRepository;

    public PedidoController(PedidoService pedidoService, PagamentoService pagamentoService, UsuarioRepository usuarioRepository, UnidadeRepository unidadeRepository, CardapioRepository cardapioRepository) {
        this.pedidoService = pedidoService;
        this.pagamentoService = pagamentoService;
        this.usuarioRepository = usuarioRepository;
        this.unidadeRepository = unidadeRepository;
        this.cardapioRepository = cardapioRepository;
    }


    @PostMapping
    public ResponseEntity<Pedido> cadastrar(@RequestBody PedidoRequest request) {

        Pedido pedido = new Pedido();

        pedido.setCanalPedido(request.canalPedido());
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setTotal(BigDecimal.ZERO);

        //usuário
        Usuario usuario = usuarioRepository.findById(request.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        pedido.setUsuario(usuario);

        //unidade
        Unidade unidade = unidadeRepository.findById(request.unidadeId())
                .orElseThrow(() -> new RuntimeException("Unidade não encontrada"));

        pedido.setUnidade(unidade);

        Pagamento pagamento = new Pagamento();
        pagamento.setFormaPagamento(request.formaPagamento());
        pagamento.setData(LocalDateTime.now());

        List<ItensPedido> itens = request.itens().stream().map(i -> {

            ItensPedido item = new ItensPedido();

            Cardapio cardapio = cardapioRepository.findById(i.cardapioId())
                    .orElseThrow(() -> new RuntimeException("Cardápio não encontrado"));

            item.setCardapio(cardapio);
            item.setQuantidade(i.quantidade());
            item.setPrecoUnitario(i.precoUnitario());

            return item;
        }).toList();

        return ResponseEntity.status(201)
                .body(pedidoService.cadastrarPedido(pedido, pagamento, itens));
    }
    @GetMapping
    public ResponseEntity<List<Pedido>> listarTodos() {
        return ResponseEntity.ok(pedidoService.listarTodos());
    }

    @GetMapping("/canal")
    public ResponseEntity<List<Pedido>> listarPorCanal(@RequestParam CanalPedido canalPedido) {
        return ResponseEntity.ok(pedidoService.listarPorCanal(canalPedido));
    }

    @GetMapping("/status")
    public ResponseEntity<List<Pedido>> listarPorStatus(@RequestParam StatusPedido status) {
        return ResponseEntity.ok(pedidoService.listarPorStatus(status));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Pedido> atualizarStatus(
            @PathVariable Long id,
            @RequestParam StatusPedido novoStatus) {

        System.out.println("ID RECEBIDO: " + id);
        System.out.println("STATUS RECEBIDO: " + novoStatus);

        return ResponseEntity.ok(
                pedidoService.atualizarStatus(id, novoStatus)
        );
    }

    @PatchMapping("/{id}/pagamento/aprovar")
    public ResponseEntity<Pedido> aprovarPagamento(@PathVariable Long id) {

        pagamentoService.aprovarPagamento(id);

        return ResponseEntity.ok(
                pedidoService.processarPagamentoAprovado(id)
        );
    }

    @PatchMapping("/{id}/pagamento/recusar")
    public ResponseEntity<Pedido> recusarPagamento(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.recusarPedido(id));
    }
}