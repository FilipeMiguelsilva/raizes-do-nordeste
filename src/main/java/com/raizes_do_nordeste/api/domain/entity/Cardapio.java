package com.raizes_do_nordeste.api.domain.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity

public class Cardapio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_unidade", nullable = false)
    private Unidade unidade;

    @ManyToOne
    @JoinColumn(name = "id_produto", nullable = false)
    private Produto produto;

    @Column(nullable = false)
    private Boolean status;
}
