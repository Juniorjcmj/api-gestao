package com.jcmj.core.modulos.financeiro.model;

import com.jcmj.core.modulos.empresa.model.Empresa;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
public class ContasPagar extends PanacheEntity {

    @NotNull(message = "A data de vencimento é obrigatória.")
    @Column(name="data_vencimento")
    public LocalDate dataVencimento;

    @Column(name="data_pagamento")
    public LocalDate dataPagamento;

    @NotNull(message = "O valor da duplicata é obrigatória.")
    @Column(name = "valor_duplicata", nullable = false, columnDefinition = "DECIMAL(9,2) DEFAULT 0.00" )
    public BigDecimal valorDuplicata;

    @Column(name="juros_multa", nullable = false, columnDefinition = "DECIMAL(9,2) DEFAULT 0.00")
    public BigDecimal jurosMulta;

    @Column(name = "valor_pago", nullable = true, columnDefinition = "DECIMAL(9,2) DEFAULT 0.00")
    public BigDecimal valorPago;

    @Column(name = "desconto", nullable = true, columnDefinition = "DECIMAL(9,2) DEFAULT 0.00")
    public BigDecimal desconto;

    @Size(max = 255,min = 3 , message = "Nome deve conter no mínimo 3 caracteres.")
    @NotEmpty(message = "o Nome do fornecedor é obrigatório.")
    @Column(name = "fornecedor")
    public String fornecedor;

    public String nd;

    public String parcela;

    @Column(name="local_pagamento")
    public String localPagamento;

    @Column(name="forma_pagamento")
    public String formaPagamento;

    @NotEmpty(message = "Tipo de despesa deve ser preenchido.")
    @Column(name="tipo_despesa")
    public String tipoDespesa;

    @Column(name="classificacao_despesa")
    public String classificacaoDespesa;

    @Column(name="sub_classificacao_despesa")
    public String subClassificacaoDespesa;

    @Column(name = "situacao", columnDefinition="varchar(50) default 'PENDENTE'")
    public String situacao;

    public String observacao;

    @Column(name="is_aprovado")
    public Boolean isAprovado;

    @Column(name="is_pedir_boleto")
    public Boolean isPedirBoleto;

    @OneToOne
    @JoinColumn(name = "documento_id")
    public Documento documento;

    @Column(name="numero_parcelas")
    public Integer numeroParcelas;

    @NotNull(message = "Empresa é obrigatória.")
    @OneToOne
    @JoinColumn(name = "empresa_id")
    public Empresa empresa;

    public static List<ContasPagar> findByNumeroDocumento(String numeroDocumento) {
        return find("nd", numeroDocumento).list();
    }
    public static List<ContasPagar> findByDataVenvimento(LocalDate dtVencimento) {
        return find("dataVencimento", dtVencimento).list();
    }
    public static List<ContasPagar> findByDataPagamento(LocalDate dtPagamento) {
        return find("dataPagamento", dtPagamento).list();
    }
    public static List<ContasPagar> findByFornecedor(String fornecedor ) {
        return find("fornecedor", fornecedor).list();
    }
    public static List<ContasPagar> findByEmptresa(Empresa empresa ) {
        return find("empresa", empresa).list();
    }

}
