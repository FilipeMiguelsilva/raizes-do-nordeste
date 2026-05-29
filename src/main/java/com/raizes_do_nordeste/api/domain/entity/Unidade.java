package com.raizes_do_nordeste.api.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity

public class Unidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String endereco;

    @Column(nullable = false)
    private String cidade;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    private Boolean status;


}
