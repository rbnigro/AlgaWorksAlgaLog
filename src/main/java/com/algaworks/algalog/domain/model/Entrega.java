package com.algaworks.algalog.domain.model;

import com.algaworks.algalog.domain.ValildationsGroups;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

//@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity(name = "entrega")
public class Entrega {

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @ConvertGroup(to = ValildationsGroups.ClienteId.class)
    @JoinColumn(name = "cliente_id")
    @NotNull
    @Valid
    private Cliente cliente;

    @Valid
    @Embedded
    @NotNull
    private Destinatario destinatario;

    @NotNull
    @Column(name = "taxa")
    private BigDecimal taxa;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusEntrega status;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "data_pedido")
    private OffsetDateTime dataPedido;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "data_finalizado")
    private OffsetDateTime dataFinalizado;

    @OneToMany(mappedBy = "entrega")
    private List<Ocorrencia> ocorrencias = new ArrayList<>();

    public Ocorrencia adicionarOcorrencia(String descricao) {
        var ocorrencia = new Ocorrencia();
        ocorrencia.setDescricao(descricao);
        ocorrencia.setDetaRegistro(OffsetDateTime.now());
        ocorrencia.setEntrega(this);
        this.getOcorrencias().add(ocorrencia);

        return ocorrencia;
    }

    // @Transient -> não vai para o banco
}
