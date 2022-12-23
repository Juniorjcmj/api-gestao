package com.jcmj.core.modulos.conciliacao.repository;

import com.jcmj.core.modulos.conciliacao.model.ConciliacaoCartoes;
import com.jcmj.core.modulos.conciliacao.model.OperadoraCartao;
import com.jcmj.core.modulos.empresa.model.Empresa;
import com.jcmj.core.modulos.financeiro.model.ContasPagar;

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

@ApplicationScoped
public class ConciliacaoCartaoRepository {

    @PersistenceContext
    EntityManager manager;

    /**
     *
     * @param aute
     * @param numeroPedido
     * @param dtInicio
     * @param dtFim
     * @param idEmpresa
     * @param idOperadora
     * @param tipoOperacao
     * @param isRecebido
     * @param previsaoRecebimento
     * @param dataRecebimento
     * @return
     * @throws ParseException
     */
    public List<ConciliacaoCartoes> findByAllAvancado(String aute, String numeroPedido, String dtInicio, String dtFim,
                                               String idEmpresa, String idOperadora, String tipoOperacao,
                                               String isRecebido, String previsaoRecebimento, String dataRecebimento) throws ParseException {

        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<ConciliacaoCartoes> criteria = builder.createQuery(ConciliacaoCartoes.class);
        var root = criteria.from(ConciliacaoCartoes.class);

        var predicates = new ArrayList<Predicate>();

        if(!(dtFim.equalsIgnoreCase("null")) && !(dtFim.isEmpty())&&
                !(dtInicio.equalsIgnoreCase("null")) && !(dtInicio.isEmpty())){
            predicates.add(builder.between(root.get("data"), LocalDate.parse(dtInicio) ,LocalDate.parse(dtFim)));
        }else if(!(dtInicio.equalsIgnoreCase("null")) && !(dtInicio.isEmpty())){
            predicates.add(builder.equal(root.get("data"), LocalDate.parse(dtInicio)));
        }
        if(!(previsaoRecebimento.equalsIgnoreCase("null") ) && (!previsaoRecebimento.isEmpty()) ){
            predicates.add(builder.equal(root.get("previsaoRecebimento"), LocalDate.parse(dtInicio)));
        }
        if(!(dataRecebimento.equalsIgnoreCase("null") ) && (!dataRecebimento.isEmpty()) ){
            predicates.add(builder.equal(root.get("dataRecebimento"), LocalDate.parse(dataRecebimento)));
        }

        if(!(numeroPedido.equalsIgnoreCase("null") ) && (!numeroPedido.isEmpty()) ){
            predicates.add(builder.like(root.get("numeroPedido"), numeroPedido));
        }
        if(!(aute.equalsIgnoreCase("null") ) && (!aute.isEmpty()) ){
            predicates.add(builder.like(root.get("aute"), "%"+aute+"%"));
        }
        if(!(tipoOperacao.equalsIgnoreCase("null") ) && (!tipoOperacao.isEmpty()) ){
            predicates.add(builder.like(root.get("tipoOperacao"), tipoOperacao));
        }
        if(!(isRecebido.equalsIgnoreCase("null") ) && (!isRecebido.isEmpty()) ){
            predicates.add(builder.equal(root.get("isRecebido"), Boolean.parseBoolean(isRecebido)));
        }

        if(!(idEmpresa.equalsIgnoreCase("null") ) && (!idEmpresa.isEmpty())){
            Empresa empresaFilter = Empresa.findById(Long.parseLong(idEmpresa));
            predicates.add(builder.equal(root.get("empresa"),empresaFilter ));
        }
        if(!(idOperadora.equalsIgnoreCase("null") ) && (!idOperadora.isEmpty())){
            OperadoraCartao operadoraFilter = OperadoraCartao.findById(Long.parseLong(idOperadora));
            predicates.add(builder.equal(root.get("operadora"),operadoraFilter ));
        }

        List<Order> ordenacao = new ArrayList<Order>();
        ordenacao.add(builder.asc(root.get("data")));

        criteria.orderBy(ordenacao);

        criteria.where(predicates.toArray(new Predicate[0]));

        TypedQuery<ConciliacaoCartoes> query = manager.createQuery(criteria);
        return query.getResultList();
    }

}
