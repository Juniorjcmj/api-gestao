package com.jcmj.core.modulos.financeiro.enuns;

public enum Situacao {
    PAGO("PAGO"),
    PENDENTE("PENDENTE"),
    POSTERGADO("POSTERGADO"),
    CANCELADO("CANCELADO");

    private String descricao;

    private Situacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
