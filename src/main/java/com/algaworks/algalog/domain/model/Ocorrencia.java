package com.algaworks.algalog.domain.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Getter
@Setter
@Entity(name = "ocorrencia")
public class Ocorrencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "entrega_id")
    private Entrega entrega;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "data_registro")
    private OffsetDateTime detaRegistro;


}
