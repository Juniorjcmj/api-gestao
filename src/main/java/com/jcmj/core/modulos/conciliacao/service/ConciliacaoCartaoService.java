package com.jcmj.core.modulos.conciliacao.service;

import com.jcmj.exceptions.DataIntegrityException;
import com.jcmj.exceptions.EntidadeEmUsoException;
import com.jcmj.core.modulos.conciliacao.factory.ConciliacaoBuilder;
import com.jcmj.core.modulos.conciliacao.model.ConciliacaoCartoes;
import com.jcmj.core.modulos.conciliacao.model.OperadoraCartao;
import com.jcmj.core.modulos.empresa.model.Empresa;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ConciliacaoCartaoService {
    public List<ConciliacaoCartoes> persist(ConciliacaoCartoes model) {
        try {
            model.aute = (model.aute != null ? model.aute : "N/C: "+ model.id);
            OperadoraCartao operadora = OperadoraCartao.findById(model.operadora.id);
            Empresa empresa = Empresa.findById(model.empresa.id);

            if(model.id != null){
                model = new ConciliacaoBuilder(model)
                        .addValorPedido(model.valorPedido)
                        .addEmpresa(empresa)
                        .addOperadora(operadora)
                        .addIsfoiConferido(model.foiConferido)
                        .addQuemCadastrou(model.quemCadastrou)
                        .addTipoOperacao(model.tipoOperacao)
                        .addData(model.data)
                        .addNumeroPedido(model.numeroPedido)
                        .addAute(model.aute)
                        .addPrevisaoRecebimento()
                        .addValorReceber()
                        .construir();
            }else{
                model = new ConciliacaoBuilder()
                        .addValorPedido(model.valorPedido)
                        .addEmpresa(empresa)
                        .addOperadora(operadora)
                        .addIsfoiConferido(model.foiConferido)
                        .addQuemConferiu(model.quemConferiu)
                        .addTipoOperacao(model.tipoOperacao)
                        .addData(model.data)
                        .addNumeroPedido(model.numeroPedido)
                        .addAute(model.aute)
                        .addPrevisaoRecebimento()
                        .addValorReceber()
                        .construir();
            }
            model.persist();
            return ConciliacaoCartoes.findByNumeroPedido(model.numeroPedido);
        }catch (DataIntegrityException e) {
            throw  new EntidadeEmUsoException("Número de autenticação já existe!");
        }
        catch (Exception e) {
            throw  new EntidadeEmUsoException(e.getMessage());
        }
    }
}
