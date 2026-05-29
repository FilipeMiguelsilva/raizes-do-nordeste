package com.raizes_do_nordeste.api.domain.entity;


import java.math.BigDecimal;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity

public class ItensPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_pedido", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "id_cardapio", nullable = false)
    private Cardapio cardapio;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false)
    private BigDecimal precoUnitario;

}
