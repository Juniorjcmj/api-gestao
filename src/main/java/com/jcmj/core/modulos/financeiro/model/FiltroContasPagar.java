package com.jcmj.core.modulos.financeiro.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FiltroContasPagar {


    private String dataVencimentoInicial;
    private String dataVencimentoFinal;
    private String dataPagamentoInicial;
    private String dataPagamentoFinal;
    private String fornecedor;
    private String nd;
    private String localPagamento;
    private List<String> formaPagamento;
    private String tipoDespesa;
    private String situacao;
    private List<String> empresaId;
    private List<String> classificacaoDespesa;
    private List<String> subClassificacaoDespesa;

}
