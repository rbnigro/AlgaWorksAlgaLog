package com.algaworks.algalog.domain.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Embeddable
@Getter
@Setter
public class Destinatario {

    @NotBlank
    @Column(name = "destinatario_nome")
    private String nome;

    @NotBlank
    @Column(name = "destinatario_logradouro")
    private String logradouro;

    @NotBlank
    @Column(name = "destinatario_numero")
    private String numero;

    @Column(name = "destinatario_complemento")
    private String complemento;

    @NotBlank
    @Column(name = "destinatario_bairro")
    private String bairro;

    @Override
    public String toString() {
        return "Destinatario{" +
                "nome='" + this.getNome() + '\'' +
                ", logradouro='" + this.getLogradouro() + '\'' +
                ", numero='" + this.getNumero() + '\'' +
                ", complemento='" + this.getComplemento() + '\'' +
                ", bairro='" + this.getBairro() + '\'' +
                '}';
    }
}
