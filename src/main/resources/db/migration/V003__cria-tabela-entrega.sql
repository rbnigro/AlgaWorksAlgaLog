create table entrega(
    id bigint not null auto_increment COMMENT 'PK',
    cliente_id bigint not null,
    taxa decimal(10, 2) not null,
    status varchar(20),
    data_pedido datetime not null,
    data_finalizado datetime,

    destinatario_nome varchar(60) not null,
    destinatario_logradouro varchar(255) not null,
    destinatario_numero varchar(30) not null,
    destinatario_complemento varchar(60),
    destinatario_bairro varchar(30) not null,

    primary key (id)
);

alter TABLE entrega add constraint fk_entrega_cliente foreign key (cliente_id) references cliente (id);