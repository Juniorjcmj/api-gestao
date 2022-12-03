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
    @NotNull(message = "data é obrigatória.")
    private LocalDate data;
    private LocalDate dataRecebimento;
    @NotBlank(message="Numero pedido é obrigatório")
    private String numeroPedido;
    @NotBlank(message="Tipo é obrigatório")
    private String tipoOperacao;
    private String valorPedido;
    private Long idOperadora;
    private Long idEmpresa;
    private String aute;
    public String  quemCadastrou;
    public String quemConferiu;
}
