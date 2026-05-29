package com.raizes_do_nordeste.api.domain.entity;

import java.time.LocalDateTime;
import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity

public class Fidelizacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private Integer pontos;

    @Column(nullable = false)
    private Boolean utilizar;

    @Column(nullable = false)
    private Boolean consentLGPD;

    @Column(nullable = false)
    private LocalDateTime data;

}
