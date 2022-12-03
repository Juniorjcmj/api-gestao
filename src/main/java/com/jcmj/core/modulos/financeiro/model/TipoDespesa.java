package com.jcmj.core.modulos.financeiro.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class TipoDespesa extends PanacheEntity {

    public String descricao;

    /**
     * Método responsavél por filtrar descrição
     * @param descricao
     * @return TipoDespesa
     */
    public static TipoDespesa findByDescricao(String descricao) {
        return find("descricao", descricao).firstResult();
    }

}
