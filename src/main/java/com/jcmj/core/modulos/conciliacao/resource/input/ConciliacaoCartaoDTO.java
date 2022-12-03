package com.jcmj.core.modulos.conciliacao.resource.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConciliacaoCartaoDTO {

    private Long id;

    @NotNull(message = "data é obrigatória.")
    private LocalDate data;
    private LocalDate previsaoRecebimento;
    private LocalDate dataRecebimento;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    @NotBlank(message="bandeira é obrigatório")
    private String numeroPedido;
    private String isAntecipa;
    private String isRecebido;
    @NotBlank(message="Tipo é obrigatório")
    private String tipoOperacao;
    private String valorPedido;
    private String valorReceber;
    private String valorTaxaAntecipacao;
    private String valorTaxaPadrao;
    private String nomeOperadora;
    private Long idOperadota;
    private String nomeEmpresa;
    private Long idOEmpresa;
    private String  quemCadastrou;
    private String quemConferiu;
    private String foiConferido;
    private String aute;
    private String taxas;


}
