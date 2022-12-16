package com.jcmj.core.modulos.financeiro.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "classificacao_despesa")
public class ClassificacaoDespesa extends PanacheEntity {

    public String descricao;

    @Column(name="lista_produtos")
    @OneToMany
    @JoinTable(name="lista_sub_classificacao_despesa",
            joinColumns={@JoinColumn(name="classificacao_despesa_id",
                    referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="sub_classificacao_despesa_id",
                    referencedColumnName="id")})
    public List<SubClassificacaoDespesa> subClassificacao;

    /**
     * Método responsavél por filtrar descrição
     * @param descricao
     * @return ClassificacaoDespesa
     */
    public static ClassificacaoDespesa findByDescricao(String descricao) {
        return find("descricao", descricao).firstResult();
    }
}
