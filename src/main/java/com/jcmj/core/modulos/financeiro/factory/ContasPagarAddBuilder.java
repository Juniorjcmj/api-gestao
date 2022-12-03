package com.jcmj.core.modulos.financeiro.factory;

import com.jcmj.core.modulos.empresa.model.Empresa;
import com.jcmj.core.modulos.financeiro.enuns.Situacao;
import com.jcmj.core.modulos.financeiro.model.ContasPagar;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

@ApplicationScoped
public class ContasPagarAddBuilder {

    private ContasPagar instancia;

    public ContasPagarAddBuilder() {
        this.instancia = new ContasPagar();
    }
    public ContasPagarAddBuilder(ContasPagar conta) {
        this.instancia = conta;
    }

    public ContasPagarAddBuilder addAtributosObrigatorios(String fornecedor, String nd, String tipoDespesa,
                                                          String formaPagamento) {

        this.instancia.fornecedor = fornecedor;
        this.instancia.nd = nd;
        this.instancia.tipoDespesa = tipoDespesa;
        this.instancia.formaPagamento = formaPagamento;
        return this;
    }
    public ContasPagarAddBuilder addEmpresa(Empresa empresa) {
        this.instancia.empresa = empresa;
        return this;
    }
    public ContasPagarAddBuilder addValorDuplicata(Integer id, Integer numeroParcelas, BigDecimal valorDuplicata,
                                                   BigDecimal desconto, BigDecimal multa) {

        if (numeroParcelas > 1 && id == null) {
            this.instancia
                    .valorDuplicata = valorDuplicata.divide(new BigDecimal(numeroParcelas), 3, RoundingMode.CEILING);
            this.instancia.desconto = desconto.divide(new BigDecimal(numeroParcelas), 3, RoundingMode.CEILING);
            this.instancia.jurosMulta = multa.divide(new BigDecimal(numeroParcelas), 3, RoundingMode.CEILING);

        } else {
            this.instancia.valorDuplicata = valorDuplicata;
           this.instancia.desconto =desconto;
           this.instancia.jurosMulta = multa;
        }

        this.instancia.valorPago = this.instancia.valorDuplicata.subtract(this.instancia.desconto
                .add(this.instancia.jurosMulta));
        return this;
    }
    public ContasPagarAddBuilder addParcela(String parcela, Integer numeroParcelas, int i) {
        if (this.instancia.id != null) {
            this.instancia.parcela = parcela;
        } else {
            if (parcela.isEmpty()) {
                this.instancia.parcela = (i + 1) + "/" + numeroParcelas;
            }
            else {
                this.instancia.parcela=
                        (Integer.parseInt(parcela) + i) + "/" + (numeroParcelas + (Integer.parseInt(parcela) - 1));
            }
        }
        return this;
    }
    public ContasPagarAddBuilder addDataPagamento(LocalDate dataPagamento) {
        if (dataPagamento != null) {
            this.instancia.dataPagamento = dataPagamento;
            this.instancia.situacao = Situacao.PAGO.getDescricao();
        } else {
            this.instancia.situacao = Situacao.PENDENTE.getDescricao();
        }
        return this;
    }
    public ContasPagarAddBuilder addSituacao(Long id, String situacao) {
        if (id == null) {
            this.instancia.situacao = Situacao.PENDENTE.getDescricao();
        } else {
            this.instancia.situacao = situacao;
        }
        return this;
    }

    public ContasPagarAddBuilder addDataVencimento(LocalDate dataVencimento, Integer mes) {
        if (this.instancia.id == null) {
           this.instancia.dataVencimento = dataVencimento.plusWeeks(mes);
        } else {
            this.instancia.dataVencimento  = dataVencimento;
        }
        return this;
    }
    public ContasPagarAddBuilder addObservacao(String observacao) {

        if (observacao != null && observacao != "") {
            this.instancia.observacao  = observacao;
        }
        return this;
    }
    public ContasPagarAddBuilder addClassificacao(String classificacao) {
        if (classificacao != null && classificacao != "") {
            this.instancia.classificacaoDespesa = classificacao;
        }
        return this;
    }
    public ContasPagarAddBuilder addSubClassificacao(String subClassificacao) {
        if (subClassificacao != null && subClassificacao != "") {
            this.instancia.subClassificacaoDespesa = subClassificacao;
        }
        return this;
    }
    public ContasPagar contruir() {
        return this.instancia;
    }


}
