package com.jcmj.core.modulos.financeiro.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContasPagarDTO {
    private Integer id;
    @NotNull(message = "A data de vencimento é obrigatória.")
    private LocalDate dataVencimento;
    private LocalDate dataPagamento;
    @NotNull(message = "O valor da duplicata é obrigatória.")
    private BigDecimal valorDuplicata;
    private String jurosMulta;
    private BigDecimal valorPago;
    private String desconto;
    @NotEmpty(message = "Fornecedor deve ser preenchido.")
    private String fornecedor;
    private String nd;
    private String parcela;
    private String localPagamento;
    private String formaPagamento;
    @NotEmpty(message = "Tipo de despesa deve ser preenchido.")
    private String tipoDespesa;
    private String situacao;
    private String observacao;
    private Boolean isAprovado;
    @NotNull(message = "Empresa deve ser preenchido.")
    private String nomeEmpresa;
    private Long empresaId;
    private Integer numeroParcelas;
    private String classificacaoDespesa;
    private String subClassificacaoDespesa;
    private Boolean isPedirBoleto;
}
