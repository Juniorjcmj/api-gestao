package com.jcmj.core.modulos.financeiro;

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
}
