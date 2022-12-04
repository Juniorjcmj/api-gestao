package com.jcmj.core.modulos.financeiro.model.mapper;

import com.jcmj.core.modulos.conciliacao.model.ConciliacaoCartoes;
import com.jcmj.core.modulos.conciliacao.resource.input.ConciliacaoCartaoDTO;
import com.jcmj.core.modulos.financeiro.model.ContasPagar;
import com.jcmj.core.modulos.financeiro.model.dto.ContasPagarDTO;
import com.jcmj.core.modulos.financeiro.model.input.ContasPagarInput;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi", imports = {ConvertEmpresaToId.class})
public interface ContasPagarMapper {

    //---DAO to INPUT
    @Mapping(target = "empresa_id",expression = "java(ConvertEmpresaToId.converterEmpresaToId(contasPagar))")
    ContasPagarInput toInput(ContasPagar contasPagar);

    //--- INPUT TO DAO
    @Mapping(target = "empresa",expression = "java(ConvertEmpresaToId.convertIdToEmpresa(input.getEmpresa_id()))")
    ContasPagar toModel(ContasPagarInput input);

    //---DAO  to DTO
    @Mapping(target = "empresaId",expression = "java(ConvertEmpresaToId.converterEmpresaToId(contasPagar))")
    @Mapping(target = "nomeEmpresa",expression = "java(ConvertEmpresaToId.converterModelToNomeEmpresa(contasPagar))")
    public ContasPagarDTO daoToDto(ContasPagar contasPagar);


}
