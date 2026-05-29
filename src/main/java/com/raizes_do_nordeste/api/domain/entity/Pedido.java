package com.raizes_do_nordeste.api.domain.entity;

import com.raizes_do_nordeste.api.domain.enums.CanalPedido;
import com.raizes_do_nordeste.api.domain.enums.StatusPedido;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
@Entity

public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPedido status;

    @Column(nullable = false)
    private BigDecimal total;

    @Column(nullable = false)
    private LocalDateTime dataPedido;

    @ManyToOne
    @JoinColumn(name = "id_unidade", nullable = false)
    private Unidade unidade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CanalPedido canalPedido;



}
