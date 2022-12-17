package com.jcmj.core.modulos.financeiro.service;

import com.jcmj.core.modulos.financeiro.model.ContasPagar;

import java.text.ParseException;
import java.util.List;

public interface ContasPagarService {
    List<ContasPagar> insertComParcelas(ContasPagar contasPagar) throws ParseException;

}
