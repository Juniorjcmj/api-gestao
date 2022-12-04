package com.jcmj.core.modulos.financeiro.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class SubClassificacaoDespesa extends PanacheEntity {

    public String descricao;

    /**
     * Método responsavél por filtrar descrição
     * @param descricao
     * @return SubClassificacaoDespesa
     */
    public static SubClassificacaoDespesa findByDescricao(String descricao) {
        return find("descricao", descricao).firstResult();
    }
}
