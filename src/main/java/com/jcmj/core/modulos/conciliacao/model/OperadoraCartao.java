package com.jcmj.core.modulos.conciliacao.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "operadora_cartao")
public class OperadoraCartao extends PanacheEntity {

    @NotBlank(message="nome é obrigatório")
    public String nome;

    @NotBlank(message="bandeira é obrigatório")
    public String bandeira;
    public boolean status;

    @Column(name = "antecipacao_automatica")
    public boolean antecipacaoAutomatica;

    @Column(name = "dias_para_recebimento")
    public int diasParaRecebimento;

    @Column(name = "taxa_antecipacao_credito", nullable = true, columnDefinition = "DECIMAL(9,2) DEFAULT 0.00")
    public BigDecimal taxaAntecipacaoCredito;

    @Column(name = "taxa_padrao_credito", nullable = true, columnDefinition = "DECIMAL(9,2) DEFAULT 0.00")
    public BigDecimal taxaPadraoCredito;

    @Column(name = "taxa_antecipacao_debito", nullable = true, columnDefinition = "DECIMAL(9,2) DEFAULT 0.00")
    public BigDecimal taxaAntecipacaoDebito;

    @Column(name = "taxa_padrao_debito", nullable = true, columnDefinition = "DECIMAL(9,2) DEFAULT 0.00")
    public BigDecimal taxaPadraoDebito;



    public LocalDate inicio;
    public LocalDate fim;

    public OperadoraCartao() {
    }
    public String  getNomeBandeira(){
        return nome+"/"+bandeira;
    }

    public OperadoraCartao(Long id){
         this.id =id;
     }

     public static List<OperadoraCartao> findByAtivasOuDesativadas(Boolean situacao) {
        return find("status", situacao).list();
   }

    public String getStatusViews() {
        if(status){
            return "ATIVA";
        }
        return "INATIVA";
    }

    public String getAntecipacaoAutomaticaViews() {
        if(antecipacaoAutomatica){
            return "SIM";
        }
        return "NÃO";
    }
}
