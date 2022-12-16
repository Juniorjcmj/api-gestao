package com.jcmj.core.modulos.financeiro.model.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContasPagarUpdateGenerico {

    private Long id;
    private LocalDate dataPagamento;
    private String jurosMulta;
    private String desconto;
    private String localPagamento;
}
