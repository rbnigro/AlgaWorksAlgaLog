package com.algaworks.algalog.domain.model;

import com.algaworks.algalog.domain.ValildationsGroups;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity(name = "cliente")
public class Cliente {

    @NotNull(groups = ValildationsGroups.ClienteId.class)
    // @EqualsAndHashCode.Include
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 60)
    @Column(name = "nome")
    private String nome;

    @NotBlank
    @Email
    @Size(max = 255)
    @Column(name = "email")
    private String email;

    @NotBlank
    @Size(max = 20)
    @Column(name = "fone")
    private String fone;


}
