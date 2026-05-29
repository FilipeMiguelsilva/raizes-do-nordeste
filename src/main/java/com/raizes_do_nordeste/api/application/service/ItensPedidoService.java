package com.raizes_do_nordeste.api.application.service;

import com.raizes_do_nordeste.api.domain.entity.ItensPedido;
import com.raizes_do_nordeste.api.infrastructure.repository.ItensPedidoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ItensPedidoService {

    private final ItensPedidoRepository itensPedidoRepository;


    public ItensPedidoService(ItensPedidoRepository itensPedidoRepository) {
        this.itensPedidoRepository = itensPedidoRepository;
    }


    //lista os itens por id do pedido
    public List<ItensPedido> buscarPorPedidoId(Long pedidoId) {

        List<ItensPedido> itens = itensPedidoRepository.findByPedidoId(pedidoId);

        if (itens.isEmpty()) {
            throw new RuntimeException("Nenhum item encontrado para o pedido: " + pedidoId);
        }

        return itens;

    }

    public ItensPedido adicionar(ItensPedido itensPedido){

      return itensPedidoRepository.save(itensPedido);

    }

    //primeiro verifica a existencia do pedido pra deletar
    public void deletar(Long id) {

        if (!itensPedidoRepository.existsById(id)) {
            throw new RuntimeException("Item não encontrado");
        }

        itensPedidoRepository.deleteById(id);
    }


}

