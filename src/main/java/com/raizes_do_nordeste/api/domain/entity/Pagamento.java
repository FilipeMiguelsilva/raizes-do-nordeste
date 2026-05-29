package com.raizes_do_nordeste.api.domain.entity;

import com.raizes_do_nordeste.api.domain.enums.FormaPagamento;
import com.raizes_do_nordeste.api.domain.enums.StatusPagamento;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity

public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_pedido", nullable = false)
    private Pedido pedido;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPagamento statusPagamento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FormaPagamento formaPagamento;

    @Column(name = "data_pagamento")
    private LocalDateTime data;

}