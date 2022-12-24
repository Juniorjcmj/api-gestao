package com.jcmj.core.modulos.conciliacao.resource.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConciliacaoCartaoInput {

    private Long id;

    private LocalDate data;
    private LocalDate dataRecebimento;

    private String numeroPedido;

    private String tipoOperacao;
    private String valorPedido;
    private Long idOperadora;
    private Long idEmpresa;
    private String aute;
    public String  quemCadastrou;
    public String quemConferiu;
}
