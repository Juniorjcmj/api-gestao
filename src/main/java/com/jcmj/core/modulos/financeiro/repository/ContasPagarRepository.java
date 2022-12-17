package com.jcmj.core.modulos.financeiro.repository;

import com.jcmj.core.modulos.empresa.model.Empresa;
import com.jcmj.core.modulos.financeiro.model.ContasPagar;
import com.jcmj.core.modulos.financeiro.model.FiltroContasPagar;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.hibernate.criterion.Restrictions;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ContasPagarRepository implements PanacheRepository<ContasPagar> {

    @PersistenceContext
    private EntityManager manager;


    public List<ContasPagar> findByAllAvancadissimo(FiltroContasPagar filtro) throws ParseException {

        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<ContasPagar> criteria = builder.createQuery(ContasPagar.class);
        var root = criteria.from(ContasPagar.class);

        var predicates = new ArrayList<Predicate>();

        if(filtro.getDataVencimentoFinal() != null){
            predicates.add(builder.between(root.get("dataVencimento"), LocalDate.parse(filtro.getDataVencimentoInicial()) ,LocalDate.parse(filtro.getDataVencimentoFinal())));
        }else if(filtro.getDataVencimentoInicial() != null){
            predicates.add(builder.equal(root.get("dataVencimento"), LocalDate.parse(filtro.getDataVencimentoInicial())));
        }

        if(filtro.getDataPagamentoFinal() != null){
            predicates.add(builder.between(root.get("dataPagamento"), LocalDate.parse(filtro.getDataPagamentoInicial()) ,LocalDate.parse(filtro.getDataPagamentoFinal())));
        }else if(filtro.getDataPagamentoInicial() != null){
            predicates.add(builder.equal(root.get("dataPagamento"), LocalDate.parse(filtro.getDataPagamentoInicial())));
        }



        if(filtro.getTipoDespesa() != null ){
            predicates.add(builder.like(root.get("tipoDespesa"), filtro.getTipoDespesa()));
        }
        if(filtro.getSituacao() != null ){
            predicates.add(builder.like(root.get("situacao"), filtro.getSituacao()));
        }
        if(filtro.getNd() != null ){
            predicates.add(builder.like(root.get("nd"), filtro.getNd()));
        }
        if(filtro.getFornecedor()!= null ){
            predicates.add(builder.like(root.get("fornecedor"), filtro.getFornecedor()+"%"));
        }


        if( filtro.getFormaPagamento() != null && (!filtro.getFormaPagamento().isEmpty())){

            var predicate= new ArrayList<Predicate>();
            for(int i = 0; i < filtro.getFormaPagamento().size(); i++){

                predicate.add(builder.like(root.get("formaPagamento"), filtro.getFormaPagamento().get(i)));

            }
            predicates.add(builder.or(predicate.toArray(new Predicate[0])));
        }

        if(filtro.getEmpresaId() != null && (!(filtro.getEmpresaId().isEmpty()))){

            var predicate= new ArrayList<Predicate>();
            for (int i = 0 ; i < filtro.getEmpresaId().size(); i ++){

                Empresa empresaFilter = Empresa.findById(Long.parseLong(filtro.getEmpresaId().get(i)));
                predicate.add(builder.equal(root.get("empresa"),empresaFilter ));

            }
            predicates.add(builder.or(predicate.toArray(new Predicate[0])));
        }


        if(filtro.getClassificacaoDespesa() != null && (!(filtro.getClassificacaoDespesa().isEmpty())) && filtro.getSubClassificacaoDespesa().isEmpty()){

            var predicate= new ArrayList<Predicate>();
            for(int i = 0; i < filtro.getClassificacaoDespesa().size() ; i++){

                predicate.add(builder.equal(root.get("classificacaoDespesa"), filtro.getClassificacaoDespesa().get(i)));
            }
            predicates.add(builder.or(predicate.toArray(new Predicate[0])));
        }
       if( (filtro.getSubClassificacaoDespesa() != null && !(filtro.getSubClassificacaoDespesa().isEmpty()))){

           var predicate= new ArrayList<Predicate>();
           for(int i = 0; i < filtro.getSubClassificacaoDespesa().size(); i++){

               predicate.add(builder.equal(root.get("subClassificacaoDespesa"), filtro.getSubClassificacaoDespesa().get(i)));
           }
           predicates.add(builder.or(predicate.toArray(new Predicate[0])));
       }


        List<Order> ordenacao = new ArrayList<Order>();
        ordenacao.add(builder.asc(root.get("dataVencimento")));
        ordenacao.add(builder.asc(root.get("fornecedor")));

        criteria.orderBy(ordenacao);

        criteria.where(predicates.toArray(new Predicate[0]));

        TypedQuery<ContasPagar> query = manager.createQuery(criteria);
        return query.getResultList();
    }



}
