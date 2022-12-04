package com.jcmj.core.modulos.financeiro.model.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContasPagarInput {
    private Integer id;
    private LocalDate dataVencimento;
    private LocalDate dataPagamento;
    private String valorDuplicata;
    private String fornecedor;
    private String nd;
    private String parcela;
    private String formaPagamento;
    private String tipoDespesa;
    private String observacao;
    private Integer numeroParcelas;
    private Long empresa_id;
    private String classificacaoDespesa;
    private String subClassificacaoDespesa;
    private Boolean isPedirBoleto;


}
