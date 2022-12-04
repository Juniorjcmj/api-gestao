package com.jcmj.core.modulos.financeiro.serviceimpl;

import com.jcmj.core.modulos.financeiro.factory.ContasPagarAddBuilder;
import com.jcmj.core.modulos.financeiro.model.ContasPagar;
import com.jcmj.core.modulos.financeiro.repository.ContasPagarRepository;
import com.jcmj.core.modulos.financeiro.service.ContasPagarService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class ContasPagarServiceImpl implements ContasPagarService {
    @Inject
    ContasPagarRepository repository;
    @Override
    public List<ContasPagar> insertComParcelas(ContasPagar contasPagar) {
        contasPagar.numeroParcelas = contasPagar.numeroParcelas == null ? 1 : contasPagar.numeroParcelas;
        int quantasVouInserir = contasPagar.id == null ? contasPagar.numeroParcelas  : 1;

        for(int i = 0; i< quantasVouInserir ; i++){
            if(contasPagar.id != null){
                ContasPagar contaExistente = ContasPagar.findById(contasPagar.id);
                 insertStrategie(contasPagar,i, contaExistente).persist();
            }else{
                insertStrategie(contasPagar,i, new ContasPagar()).persist();
            }
        }
        return repository.findByNumeroDocumento(contasPagar.nd);
    }

    private ContasPagar insertStrategie(ContasPagar novaConta, int i, ContasPagar contaExistente) {

        if (contaExistente.id == null) {
            contaExistente = new ContasPagarAddBuilder()
                    .addAtributosObrigatorios(novaConta.fornecedor,  novaConta.nd,
                            novaConta.tipoDespesa, novaConta.formaPagamento, novaConta.numeroParcelas, novaConta.isPedirBoleto)
                    .addDataVencimento(novaConta.dataVencimento, i)
                    .addEmpresa(novaConta.empresa)
                    .addValorDuplicata(novaConta.id, novaConta.numeroParcelas, novaConta.valorDuplicata,
                            novaConta.desconto, novaConta.jurosMulta)
                    .addDataPagamento(novaConta.dataPagamento)
                    .addParcela("", novaConta.numeroParcelas, i)
                    .addObservacao(novaConta.observacao)
                    .addSubClassificacao(novaConta.subClassificacaoDespesa)
                    .addClassificacao(novaConta.classificacaoDespesa)
                    .contruir();
        } else {
            contaExistente = new ContasPagarAddBuilder(contaExistente)
                    .addAtributosObrigatorios(novaConta.fornecedor,
                                              novaConta.nd,
                                              novaConta.tipoDespesa,
                                              novaConta.formaPagamento,
                                              novaConta.numeroParcelas,
                                              novaConta.isPedirBoleto)
                    .addDataVencimento(novaConta.dataVencimento, i)
                    .addEmpresa(novaConta.empresa)
                    .addValorDuplicata(novaConta.id,novaConta.numeroParcelas, novaConta.valorDuplicata,
                            novaConta.desconto, novaConta.jurosMulta)
                    .addDataPagamento(novaConta.dataPagamento)
                    .addParcela(novaConta.parcela, novaConta.numeroParcelas, i)
                    .addObservacao(novaConta.observacao)
                    .addSubClassificacao(novaConta.subClassificacaoDespesa)
                    .addClassificacao(novaConta.classificacaoDespesa)
                    .contruir();
        }
        return contaExistente;

    }
}
