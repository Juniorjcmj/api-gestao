package com.jcmj.core.modulos.financeiro.model.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContasPagarInput {
    private Integer id;
    private String dataVencimento;
    private String dataPagamento;
    private String valorDuplicata;
    private String fornecedor;
    private String nd;
    private String parcela;
    private String formaPagamento;
    private String tipoDespesa;
    private String observacao;
    private Integer numeroParcelas;
    private Integer empresa_id;
}
