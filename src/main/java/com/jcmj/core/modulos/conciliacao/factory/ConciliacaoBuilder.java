package com.jcmj.core.modulos.conciliacao.factory;

import com.jcmj.core.modulos.conciliacao.model.ConciliacaoCartoes;
import com.jcmj.core.modulos.conciliacao.model.OperadoraCartao;
import com.jcmj.core.modulos.empresa.model.Empresa;

import java.math.BigDecimal;
import java.time.LocalDate;


public class ConciliacaoBuilder {
    private ConciliacaoCartoes instancia;

    public ConciliacaoBuilder () {
        this.instancia = new ConciliacaoCartoes();
    }
    public ConciliacaoBuilder (ConciliacaoCartoes conciliacao) {
        this.instancia = conciliacao;
    }

    public ConciliacaoBuilder addEmpresa(Empresa empresa) {
        this.instancia.empresa = empresa ;
        return this;
    }
    public ConciliacaoBuilder addOperadora(OperadoraCartao operadora) {
        this.instancia.operadora =operadora;
        return this;
    }
    public ConciliacaoBuilder addData(LocalDate data) {
        this.instancia.data= data;
        return this;
    }
    public ConciliacaoBuilder addDataRecebimento(LocalDate data) {
        this.instancia.dataRecebimento = data;
        return this;
    }

    public ConciliacaoBuilder addValorPedido(BigDecimal valorPedido) {
        this.instancia.valorPedido = valorPedido;
        return this;
    }
    public ConciliacaoBuilder addValorReceber() {

        if(this.instancia.isAntecipa || (this.instancia.operadora.antecipacaoAutomatica && this.instancia.tipoOperacao.equals("CRÉDITO"))){

            this.instancia.valorReceber = (this.instancia.valorPedido.subtract(this.instancia.valorTaxaAntecipacao).subtract(this.instancia.valorTaxaPadrao));
            this.instancia.isAntecipa = true ;

        }else{
            this.instancia.valorReceber = (this.instancia.valorPedido.subtract(this.instancia.valorTaxaPadrao));
        }

        return this;
    }

    public ConciliacaoBuilder addIsAntecipa(Boolean valor) {
        this.instancia.isAntecipa = valor;
        return this;
    }
    public ConciliacaoBuilder addTipoOperacao(String tipoOperacao) {
        this.instancia.tipoOperacao = tipoOperacao;

        if(this.instancia.tipoOperacao.equals("DÉBITO")){
            var valorTotalMultiply =  this.instancia.valorPedido.multiply(this.instancia.operadora.taxaPadraoDebito);
            var valorTotalDivide= valorTotalMultiply.divide(new BigDecimal("100"));
            this.instancia.valorTaxaPadrao = valorTotalDivide;
        }else if(this.instancia.tipoOperacao.equals("CRÉDITO")){
            var valorTotalMultiply =  this.instancia.valorPedido.multiply(this.instancia.operadora.taxaAntecipacaoCredito);
            var valorTotalDivide= valorTotalMultiply.divide(new BigDecimal("100"));
            this.instancia.valorTaxaPadrao = valorTotalDivide;
        }
        if(this.instancia.tipoOperacao.equals("CRÉDITO")){
            var novaTaxa = this.instancia.operadora.taxaPadraoCredito.add(this.instancia.operadora.taxaAntecipacaoCredito);
            var valorTotalMultiply =  this.instancia.valorPedido.multiply(novaTaxa);
            var valorTotalDivide= valorTotalMultiply.divide(new BigDecimal("100"));
            this.instancia.valorTaxaAntecipacao = (valorTotalDivide.subtract(this.instancia.valorTaxaPadrao));
        }


        return this;
    }
    public ConciliacaoBuilder addIsfoiConferido(Boolean valor) {
        this.instancia.foiConferido = valor;
        return this;
    }
    public ConciliacaoBuilder addNumeroPedido(String numero) {
        this.instancia.numeroPedido = numero;
        return this;
    }
    public ConciliacaoBuilder addIsRecebido(Boolean isRecebido) {
        this.instancia.isRecebido= isRecebido;
        return this;
    }
    public ConciliacaoBuilder addPrevisaoRecebimento() {

        if(this.instancia.operadora.diasParaRecebimento > 1 && this.instancia.tipoOperacao.equalsIgnoreCase("CRÉDITO")){
            this.instancia.previsaoRecebimento = (this.instancia.data.plusDays( this.instancia.operadora.diasParaRecebimento));
        }else{
            this.instancia.previsaoRecebimento = this.instancia.data.plusDays(1);
        }

        return this;
    }

    public ConciliacaoBuilder addQuemCadastrou(String quemCadastrou) {
        if(this.instancia.id == null) {
            this.instancia.quemCadastrou = quemCadastrou;
        }
        return this;
    }
    public ConciliacaoBuilder addQuemConferiu(String quemConferiu) {
        if(this.instancia.foiConferido) {
            this.instancia.quemConferiu=quemConferiu;
        }
        return this;
    }

    public ConciliacaoBuilder addValorTaxaAntecipacao(BigDecimal valorTaxaAntecipacao) {

        this.instancia.valorTaxaAntecipacao = valorTaxaAntecipacao;
        return this;
    }
    public ConciliacaoBuilder addValorTaxaPadrao(BigDecimal valorTaxaPadrao) {

        this.instancia.valorTaxaPadrao = valorTaxaPadrao;
        return this;
    }
    public ConciliacaoBuilder addAute(String aute) {
        this.instancia.aute = (aute +"/"+this.instancia.numeroPedido);
        return this;
    }

    public ConciliacaoCartoes construir() {
        return this.instancia;
    }

}
