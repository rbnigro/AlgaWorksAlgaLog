package com.algaworks.algalog.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference
    private Entrega entrega;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "data_registro")
    private OffsetDateTime dataRegistro;

    @Override
    public String toString() {
        return "Ocorrencia{" +
                "id=" + this.getId() +
                ", entrega=" + this.getEntrega() +
                ", descricao='" + this.getDescricao() + '\'' +
                ", dataRegistro=" + this.getDataRegistro() +
                '}';
    }
}
