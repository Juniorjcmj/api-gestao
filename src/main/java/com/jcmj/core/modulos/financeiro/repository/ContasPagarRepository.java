package com.jcmj.core.modulos.financeiro.repository;

import com.jcmj.core.modulos.empresa.model.Empresa;
import com.jcmj.core.modulos.financeiro.model.ContasPagar;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class ContasPagarRepository implements PanacheRepository<ContasPagar> {

    public List<ContasPagar>findByPeriodo(LocalDate dtInicio, LocalDate dtFim){
        return list("SELECT c FROM ContasPagar c where m.dataVenvimento = ?1 ORDER BY DESC", dtInicio);
    }
    public  List<ContasPagar> findByDataVenvimento(LocalDate dtVencimento) {
        return find("dataVencimento", dtVencimento).list();
    }
    public  List<ContasPagar> findByDataPagamento(LocalDate dtPagamento) {
        return find("dataPagamento", dtPagamento).list();
    }
    public  List<ContasPagar> findByNumeroDocumento(String numeroDocumento) {
        return find("nd", numeroDocumento).list();
    }

    public  List<ContasPagar> findByFornecedor(String fornecedor ) {
        return find("fornecedor", fornecedor).list();
    }
    public  List<ContasPagar> findByEmptresa(Empresa empresa ) {
        return find("empresa", empresa).list();
    }

}
