package com.jcmj.core.modulos.conciliacao.model;

import com.jcmj.core.modulos.empresa.model.Empresa;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.panache.common.Page;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "conciliacao_cartoes")
public class ConciliacaoCartoes extends PanacheEntity {

    @NotNull(message = "data é obrigatória.")
    public LocalDate data;
    @Column(name = "previsao_recebimento")
    public LocalDate previsaoRecebimento;
    @Column(name = "data_recebimento")
    public LocalDate dataRecebimento;
    @CreationTimestamp
    @Column(name = "data_criacao")
    public LocalDateTime dataCriacao;
    @UpdateTimestamp
    @Column(name = "data_atualizacao")
    public LocalDateTime dataAtualizacao;
    @NotBlank(message="bandeira é obrigatório")
    @Column(name = "numero_pedido")
    public String numeroPedido;
    @Column(name = "is_antecipa")
    public boolean isAntecipa;
    @Column(name = "is_recebido")
    public boolean isRecebido;
    @NotBlank(message="Tipo é obrigatório")
    @Column(name = "tipo_operacao")
    public String tipoOperacao;
    @Column(name = "valor_pedido", nullable = true, columnDefinition = "DECIMAL(9,2) DEFAULT 0.00")
    public BigDecimal valorPedido;
    @Column(name = "valor_receber", nullable = true, columnDefinition = "DECIMAL(9,2) DEFAULT 0.00")
    public BigDecimal valorReceber;
    @Column(name = "valor_taxa_antecipacao", nullable = true, columnDefinition = "DECIMAL(9,2) DEFAULT 0.00")
    public BigDecimal valorTaxaAntecipacao;
    @Column(name = "valor_taxa_padrao", nullable = true, columnDefinition = "DECIMAL(9,2) DEFAULT 0.00")
    public BigDecimal valorTaxaPadrao;
    @NotNull(message = "Operadora é obrigatória.")
    @OneToOne
    @JoinColumn(name = "operadora_id")
    public OperadoraCartao operadora;
    @NotNull(message = "Empresa é obrigatória.")
    @OneToOne
    @JoinColumn(name = "empresa_id")
    public Empresa empresa;
    @Column(name = "quem_cadastrou")
    public String  quemCadastrou;
    @Column(name = "quem_conferiu")
    public String quemConferiu;
    @Column(name = "foi_conferido")
    public boolean foiConferido;
    @NotBlank(message="Aute é obrigatório")
    @Column(name = "aute",unique = true)
    public String aute;

    public String getNomeOperadora(){
        return operadora.nome;
    }
    public String getNomeEmpresa(){
        return empresa.nome;
    }

    public String getAute() {
        if(aute != null){
            String[] result = aute.split("/");
            return result[0];
        }
       return aute;
    }

    /**
     *  Retorna uma conciliação
     * @param aute
     * @return ConciliacaoCartoes
     */
    public static List<ConciliacaoCartoes> findByAute(String aute) {
        return find("aute", aute).list();
    }

    /**
     * Método que busca uma conciliação através do número de pedido
     * @param numeroPedido
     * @return uma Conciliação
     */
    public static List<ConciliacaoCartoes> findByNumeroPedido(String numeroPedido) {
        return find("numeroPedido", numeroPedido).list();
    }
    /**
     * Método responsável por listar conciliação recebida com paginação
     * @param pageable
     * @return List<ConciliacaoCartoes>
     */
    public static List<ConciliacaoCartoes>findByAllPageRecebidos(Page pageable){
               return find("from ConciliacaoCartoes c where isRecebido = true order by data desc").page(pageable).list();
    }


    /**
     * Método responsável por listar conciliação pendente de recebimento com paginação
     * @param pageable
     * @return List<ConciliacaoCartoes>
     */
    public static List<ConciliacaoCartoes>findByAllPageNaoRecebidos(Page pageable){
        return find("from ConciliacaoCartoes c where isRecebido = false order by data desc").page(pageable).list();
    }
    /**
     *  Método responsável por filtrar por empresa
     * @param empresa
     * @return List<ConciliacaoCartoes>
     */
    public static List<ConciliacaoCartoes> findByAllEmpresa(Empresa empresa) {
        return find("empresa", empresa).list();
    }
    /**
     *  Método responsável por filtrar por operadora
     * @param operadora
     * @return List<ConciliacaoCartoes>
     */
    public static List<ConciliacaoCartoes> findByAllOperadora(OperadoraCartao operadora) {
        return find("operadora", operadora).list();
    }
    /**
     *  Método responsável por filtrar por operadora
     * @param data
     * @return List<ConciliacaoCartoes>
     */
    public static List<ConciliacaoCartoes> findByAllData(LocalDate data) {
        return find("data", data).list();
    }

    public String getTaxas(){
        if(tipoOperacao.equals("DÉBITO")){
            return  "Taxa padrão:  "+ operadora.taxaAntecipacaoDebito+"%" + " Taxa Antecipação: " + operadora.taxaAntecipacaoDebito+"%";
        }else{
            return  "Taxa padrão:  "+ operadora.taxaPadraoCredito+"%" + " Taxa Antecipação: " + operadora.taxaAntecipacaoCredito+"%";
        }
    }


}
