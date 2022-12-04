package com.jcmj.core.modulos.financeiro.model.mapper;

import com.jcmj.core.modulos.empresa.model.Empresa;
import com.jcmj.core.modulos.financeiro.model.ContasPagar;

public class ConvertEmpresaToId {

    public static Long converterEmpresaToId(ContasPagar contas){
        return Long.parseLong(contas.empresa.id.toString());
    }
    public static Empresa convertIdToEmpresa(Object id){
        return Empresa.findById(id);
    }

    public static String converterModelToNomeEmpresa(ContasPagar contas){
        return contas.empresa.nome;
    }
}
