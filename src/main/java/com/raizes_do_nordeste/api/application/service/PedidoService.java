package com.raizes_do_nordeste.api.application.service;

import com.raizes_do_nordeste.api.domain.entity.Pagamento;
import com.raizes_do_nordeste.api.domain.entity.Pedido;
import com.raizes_do_nordeste.api.domain.enums.CanalPedido;
import com.raizes_do_nordeste.api.domain.enums.StatusPedido;
import com.raizes_do_nordeste.api.infrastructure.repository.PedidoRepository;
import com.raizes_do_nordeste.api.domain.entity.ItensPedido;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PedidoService {

    private final PagamentoService pagamentoService;
    private final PedidoRepository pedidoRepository;
    private final ItensPedidoService itensPedidoService;
    private final FidelizacaoService fidelizacaoService;

    public PedidoService(PagamentoService pagamentoService, PedidoRepository pedidoRepository,
                         ItensPedidoService itensPedidoService, FidelizacaoService fidelizacaoService) {
        this.pagamentoService = pagamentoService;
        this.pedidoRepository = pedidoRepository;
        this.itensPedidoService = itensPedidoService;
        this.fidelizacaoService = fidelizacaoService;

    }

    public Pedido cadastrarPedido(Pedido pedido, Pagamento pagamento, List<ItensPedido> itens){

        if (pedido.getCanalPedido() == null){
            throw new RuntimeException("Canal do pedido obrigatório");
        }

        pedido.setStatus(StatusPedido.AGUARDANDO_PAGAMENTO);
        Pedido pedidoCadastrado = pedidoRepository.save(pedido);

        BigDecimal total = BigDecimal.ZERO;
        for (ItensPedido item : itens) {
            item.setPedido(pedidoCadastrado);
            itensPedidoService.adicionar(item);
            total = total.add(item.getPrecoUnitario().multiply(BigDecimal.valueOf(item.getQuantidade())));
        }

        pedidoCadastrado.setTotal(total);
        pedidoRepository.save(pedidoCadastrado);

        pagamento.setPedido(pedidoCadastrado);
        pagamentoService.cadastrar(pagamento);

        return pedidoCadastrado;
    }

    private Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

    }


    public Pedido atualizarStatus(Long pedidoId, StatusPedido novoStatus) {
        Pedido pedido = buscarPorId(pedidoId);
        pedido.setStatus(novoStatus);

        if (novoStatus == StatusPedido.ENTREGUE) {
            fidelizacaoService.adicionarPontos(
                    pedido.getUsuario().getId(), 10);
        }
        return pedidoRepository.save(pedido);
    }

    //listar pelo canal pedido
    public List<Pedido> listarPorCanal(CanalPedido canalPedido) {
        return pedidoRepository.findByCanalPedido(canalPedido);
    }

    //lista os pedido pelo status
    public List<Pedido> listarPorStatus(StatusPedido status) {
        return pedidoRepository.findByStatus(status);
    }

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    public Pedido aprovarPedido(Long pedidoId) {
        Pedido pedido = buscarPorId(pedidoId);
        pagamentoService.aprovarPagamento(pedidoId);
        pedido.setStatus(StatusPedido.EM_PREPARO);
        return pedidoRepository.save(pedido);
    }

    public Pedido recusarPedido(Long pedidoId) {
        Pedido pedido = buscarPorId(pedidoId);
        pagamentoService.recusarPagamento(pedidoId);
        pedido.setStatus(StatusPedido.CANCELADO);
        return pedidoRepository.save(pedido);
    }


}


