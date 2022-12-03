create sequence hibernate_sequence start 1 increment 1;

    create table operadora_cartao (
       id int8 not null,
        antecipacao_automatica boolean,
        bandeira varchar(255),
        dias_para_recebimento int4,
        fim date,
        inicio date,
        nome varchar(255),
        status boolean not null,
        taxa_antecipacao_credito DECIMAL(9,2) DEFAULT 0.00,
        taxa_antecipacao_debito DECIMAL(9,2) DEFAULT 0.00,
        taxa_padrao_credito DECIMAL(9,2) DEFAULT 0.00,
        taxa_padrao_debito DECIMAL(9,2) DEFAULT 0.00,
        primary key (id)
    );
