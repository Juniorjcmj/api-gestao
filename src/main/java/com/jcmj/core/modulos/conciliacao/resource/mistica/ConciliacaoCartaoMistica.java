package com.jcmj.core.modulos.conciliacao.resource.mistica;

import com.jcmj.core.modulos.conciliacao.resource.input.ConciliacaoCartaoDTO;
import com.jcmj.core.modulos.conciliacao.resource.input.ConciliacaoCartaoInput;
import com.jcmj.core.modulos.conciliacao.model.ConciliacaoCartoes;
import com.jcmj.core.modulos.conciliacao.model.OperadoraCartao;
import com.jcmj.core.modulos.empresa.model.Empresa;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ConciliacaoCartaoMistica {

    public ConciliacaoCartaoDTO modelToDto(ConciliacaoCartoes model) {
        ConciliacaoCartaoDTO dto = new ConciliacaoCartaoDTO();
        dto.setId(model.id);
        dto.setIsAntecipa(model.isAntecipa ==true ? "SIM" : "NÃO");
        dto.setAute(model.aute);
        dto.setFoiConferido(model.foiConferido==true ? "SIM" : "NÃO");
        dto.setIdOEmpresa(model.empresa.id);
        dto.setNomeEmpresa(model.empresa.nome);
        dto.setIdOperadota(model.operadora.id);
        dto.setData(model.data);
        dto.setDataAtualizacao(model.dataAtualizacao);
        dto.setDataRecebimento(model.dataRecebimento);
        dto.setDataCriacao(model.dataCriacao);
        dto.setNomeOperadora(model.operadora.nome);
        dto.setPrevisaoRecebimento(model.previsaoRecebimento);
        dto.setNumeroPedido(model.numeroPedido);
        dto.setIsRecebido(model.isRecebido==true ? "SIM" : "NÃO");
        dto.setTipoOperacao(model.tipoOperacao);
        dto.setValorPedido(model.valorPedido.toString());
        dto.setValorReceber(model.valorReceber.toString());
        dto.setValorTaxaAntecipacao(model.valorTaxaAntecipacao == null ? "0,0": model.valorTaxaAntecipacao.toString());
        dto.setValorTaxaPadrao(model.valorTaxaPadrao == null ? "0,0": model.valorTaxaPadrao.toString() );
        dto.setTaxas(model.getTaxas());
        dto.setQuemCadastrou(model.quemCadastrou);
        dto.setQuemConferiu(model.quemConferiu);
        return dto;
    }
    public ConciliacaoCartoes inputToModel(ConciliacaoCartaoInput input, ConciliacaoCartoes model ) {

        if(input.getId() != null) {
            model.id = input.getId();
        }
        model.numeroPedido=input.getNumeroPedido();
        model.tipoOperacao = input.getTipoOperacao();
        model.data = input.getData();
        model.dataRecebimento = input.getDataRecebimento();
        model.valorPedido = new BigDecimal(input.getValorPedido().replace(".", "").replace(",", "."));
        model.aute  = input.getAute();
        model.operadora = new OperadoraCartao(input.getIdOperadora());
        model.empresa = new Empresa(input.getIdEmpresa());
        model.quemConferiu = input.quemConferiu;
        model.quemCadastrou = input.quemCadastrou;
        return model;
    }

    public List<ConciliacaoCartaoDTO> listDto(List<ConciliacaoCartoes>listModel){
        List<ConciliacaoCartaoDTO>listDto =  listModel.stream().map(x -> modelToDto(x)).collect(Collectors.toList());
        return listDto;
    }


}
