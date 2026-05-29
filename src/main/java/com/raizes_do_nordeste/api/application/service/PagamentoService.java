package com.raizes_do_nordeste.api.application.service;

import com.raizes_do_nordeste.api.domain.entity.Pagamento;
import com.raizes_do_nordeste.api.domain.enums.FormaPagamento;
import com.raizes_do_nordeste.api.domain.enums.StatusPagamento;
import com.raizes_do_nordeste.api.infrastructure.repository.PagamentoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;


    public PagamentoService(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    public Pagamento cadastrar(Pagamento pagamento){
        pagamento.setStatusPagamento(StatusPagamento.AGUARDANDO_PAGAMENTO);
        pagamento.setFormaPagamento(FormaPagamento.MOCK);
        return pagamentoRepository.save(pagamento);
    }

    public Pagamento buscarPorPedidoId(Long pedidoId){

        return pagamentoRepository.findByPedidoId(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));
    }

    //simulacao de pagamento APROVADO e RECUSADO
    public Pagamento aprovarPagamento(Long pedidoId){

        Pagamento pagamento = pagamentoRepository.findByPedidoId(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));
        pagamento.setData(LocalDateTime.now());
        pagamento.setStatusPagamento(StatusPagamento.APROVADO);

        return pagamentoRepository.save(pagamento);

    }

    public Pagamento recusarPagamento(Long pedidoId){

        Pagamento pagamento = pagamentoRepository.findByPedidoId(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));
        pagamento.setData(LocalDateTime.now());
        pagamento.setStatusPagamento(StatusPagamento.RECUSADO);

        return pagamentoRepository.save(pagamento);

    }


    // Listar todos os pagamentos
    public List<Pagamento> listarTodos(){
        return pagamentoRepository.findAll();
    }

}


