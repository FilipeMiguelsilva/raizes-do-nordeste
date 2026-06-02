package com.raizes_do_nordeste.api.controller.dto;

import com.raizes_do_nordeste.api.domain.enums.CanalPedido;
import com.raizes_do_nordeste.api.domain.enums.FormaPagamento;
import java.math.BigDecimal;
import java.util.List;

public record PedidoRequest(
        Long usuarioId,
        Long unidadeId,
        CanalPedido canalPedido,
        FormaPagamento formaPagamento,
        List<ItemRequest> itens
) {
    public record ItemRequest(
            Long cardapioId,
            Integer quantidade,
            BigDecimal precoUnitario
    ) {}
}