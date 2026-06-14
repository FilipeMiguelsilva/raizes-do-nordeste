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
    private final EstoqueService estoqueService;

    public PedidoService(PagamentoService pagamentoService, PedidoRepository pedidoRepository,
                         ItensPedidoService itensPedidoService, FidelizacaoService fidelizacaoService, EstoqueService estoqueService) {
        this.pagamentoService = pagamentoService;
        this.pedidoRepository = pedidoRepository;
        this.itensPedidoService = itensPedidoService;
        this.fidelizacaoService = fidelizacaoService;
        this.estoqueService = estoqueService;
    }

    public Pedido cadastrarPedido(Pedido pedido, Pagamento pagamento, List<ItensPedido> itens) {

        if (pedido.getCanalPedido() == null) {
            throw new RuntimeException("Canal do pedido obrigatório");
        }

        pedido.setStatus(StatusPedido.AGUARDANDO_PAGAMENTO);

        Long unidadeId = pedido.getUnidade().getId();
        BigDecimal total = BigDecimal.ZERO;

        //valida tudo primeiro
        for (ItensPedido item : itens) {

            Long produtoId = item.getCardapio().getProduto().getId();

            estoqueService.verificarDisponibilidade(
                    unidadeId,
                    produtoId,
                    item.getQuantidade()
            );

            total = total.add(
                    item.getPrecoUnitario()
                            .multiply(BigDecimal.valueOf(item.getQuantidade()))
            );
        }

        //salva o pedido
        pedido.setTotal(total);
        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        //salva itens
        for (ItensPedido item : itens) {
            item.setPedido(pedidoSalvo);
            itensPedidoService.adicionar(item);
        }

        //pagamento
        pagamento.setPedido(pedidoSalvo);
        pagamentoService.cadastrar(pagamento);

        return pedidoSalvo;
    }

    private Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

    }


    public Pedido atualizarStatus(Long pedidoId, StatusPedido novoStatus) {
        Pedido pedido = buscarPorId(pedidoId);
        pedido.setStatus(novoStatus);

        if (pedido.getStatus() == StatusPedido.ENTREGUE) {
            throw new RuntimeException("Pedido já foi entregue");
        }
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

    //processa o pagamento aprovado, atualizando status do pedido e baixa no estoque
    public Pedido processarPagamentoAprovado(Long pedidoId) {

        Pedido pedido = buscarPorId(pedidoId);
        Long unidadeId = pedido.getUnidade().getId();
        List<ItensPedido> itens = itensPedidoService.buscarPorPedidoId(pedido.getId());

        for (ItensPedido item : itens) {

            Long produtoId = item.getCardapio().getProduto() != null
                    ? item.getCardapio().getProduto().getId()
                    : null;

            estoqueService.removerPorUnidadeEProduto(
                    unidadeId,
                    produtoId,
                    item.getQuantidade()
            );
        }
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


