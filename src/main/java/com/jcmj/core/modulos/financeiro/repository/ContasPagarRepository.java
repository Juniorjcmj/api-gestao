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
    public List<ContasPagar>findByPeriodoVencimento(LocalDate dtInicio, LocalDate dtFim){
        return list(" FROM ContasPagar c WHERE c.dataVencimento >= ?1 AND c.dataVencimento <= ?2 ", dtInicio, dtFim);
    }
    public List<ContasPagar>findByPeriodoVencimentoFornecedor(LocalDate dtInicio, LocalDate dtFim, String fornecedor){
        return list(" FROM ContasPagar c WHERE c.fornecedor = ?3 and c.dataVencimento >= ?1 AND c.dataVencimento <= ?2 "
                           , dtInicio, dtFim, fornecedor);
    }
    public List<ContasPagar>findByPeriodoVencimentoEmpresa(LocalDate dtInicio, LocalDate dtFim, Empresa empresa){
        return list(" FROM ContasPagar c WHERE c.empresa = ?3 and c.dataVencimento >= ?1 AND c.dataVencimento <= ?2 "
                , dtInicio, dtFim, empresa);
    }
    public List<ContasPagar>findByPeriodoPagamentoEmpresa(LocalDate dtInicio, LocalDate dtFim, Empresa empresa){
        return list(" FROM ContasPagar c WHERE c.empresa = ?3 and c.dataPagamento >= ?1 AND c.dataPagamento <= ?2 "
                , dtInicio, dtFim, empresa);
    }
    public List<ContasPagar>findByPeriodoPagamentoFornecedor(LocalDate dtInicio, LocalDate dtFim, String fornecedor){
        return list(" FROM ContasPagar c WHERE c.fornecedor = ?3 and c.dataPagamento >= ?1 AND c.dataPagamento <= ?2 "
                , dtInicio, dtFim, fornecedor);
    }
    public List<ContasPagar>findByPeriodoPagamento(LocalDate dtInicio, LocalDate dtFim){
        return list(" FROM ContasPagar c WHERE c.dataPagamento >= ?1 AND c.dataPagamento <= ?2 ", dtInicio, dtFim);
    }
    public List<ContasPagar>findByPeriodoVencimentoClassificacao(LocalDate dtInicio, LocalDate dtFim, String classificacaoDespesa){
        return list(" FROM ContasPagar c WHERE c.classificacaoDespesa = ?3 and c.dataVencimento >= ?1 AND c.dataVencimento <= ?2 "
                , dtInicio, dtFim, classificacaoDespesa);
    }
    public List<ContasPagar>findByPeriodoPagamentoClassificacao(LocalDate dtInicio, LocalDate dtFim, String classificacaoDespesa){
        return list(" FROM ContasPagar c WHERE c.classificacaoDespesa = ?3 and c.dataPagamento >= ?1 AND c.dataPagamento <= ?2 "
                , dtInicio, dtFim, classificacaoDespesa);
    }
    public List<ContasPagar>findByPeriodoPagamentoSubClassificacao(LocalDate dtInicio, LocalDate dtFim, String subClassificacaoDespesa){
        return list(" FROM ContasPagar c WHERE c.subClassificacaoDespesa = ?3 and c.dataPagamento >= ?1 AND c.dataPagamento <= ?2 "
                , dtInicio, dtFim, subClassificacaoDespesa);
    }
    public List<ContasPagar>findByPeriodoVencimentoSubClassificacao(LocalDate dtInicio, LocalDate dtFim, String subClassificacaoDespesa){
        return list(" FROM ContasPagar c WHERE c.subClassificacaoDespesa = ?3 and c.dataVencimento >= ?1 AND c.dataVencimento <= ?2 "
                , dtInicio, dtFim, subClassificacaoDespesa);
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

    public  List<ContasPagar> findByTipoDespesa(String tipoDespesa ) {
        return find("tipoDespesa", tipoDespesa).list();
    }
    public List<ContasPagar>findByPeriodoVencimentoEmpresaTipoDespesa(LocalDate dtInicio, LocalDate dtFim, Empresa empresa, String tipoDespesa){
        return list(" FROM ContasPagar c WHERE c.empresa = ?3 and c.tipoDespesa = ?4 and c.dataVencimento >= ?1 AND c.dataVencimento <= ?2 "
                , dtInicio, dtFim, empresa);
    }
    public List<ContasPagar>findByPeriodoPagamentoEmpresaTipoDespesa(LocalDate dtInicio, LocalDate dtFim, Empresa empresa, String tipoDespesa){
        return list(" FROM ContasPagar c WHERE c.empresa = ?3 and c.tipoDespesa = ?4 and c.dataPagamento >= ?1 AND c.dataPagamento <= ?2 "
                , dtInicio, dtFim, empresa);
    }

    /**
     * Método Responsável por fazer filtro avançado contas a pagar
     * @param dataInicial
     * @param dataFinal
     * @param tipoDespesa
     * @param idEmpresa
     * @param situacao
     * @param forma
     * @param fornecedor
     * @return  Lista de contasPagarDto
     * @throws ParseException
     */
    public List<ContasPagar> findByAllAvancado(String dataInicial, String dataFinal, String tipoDespesa, String idEmpresa,
                                  String situacao, String forma, String fornecedor, String classificacao, String subClassificacao, String numeroDocumento) throws ParseException {

        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<ContasPagar> criteria = builder.createQuery(ContasPagar.class);
        var root = criteria.from(ContasPagar.class);

        var predicates = new ArrayList<Predicate>();

        if(!(dataFinal.equalsIgnoreCase("null") ) && (!dataFinal.isEmpty()) ){
            predicates.add(builder.between(root.get("dataVencimento"), LocalDate.parse(dataInicial) ,LocalDate.parse(dataFinal)));
        }else if(!(dataInicial.equalsIgnoreCase("null") ) && (!dataInicial.isEmpty())){
            predicates.add(builder.equal(root.get("dataVencimento"), LocalDate.parse(dataInicial)));
        }


        if(!(tipoDespesa.equalsIgnoreCase("null") ) && (!tipoDespesa.isEmpty()) ){
            predicates.add(builder.like(root.get("tipoDespesa"), tipoDespesa));
        }
        if(!(situacao.equalsIgnoreCase("null") ) && (!situacao.isEmpty()) ){
            predicates.add(builder.like(root.get("situacao"), situacao));
        }
        if(!(numeroDocumento.equalsIgnoreCase("null") ) && (!numeroDocumento.isEmpty()) ){
            predicates.add(builder.like(root.get("nd"), numeroDocumento));
        }
        if(!(forma.equalsIgnoreCase("null") ) && (!forma.isEmpty()) ){
            predicates.add(builder.like(root.get("formaPagamento"), forma));
        }
        if(!(idEmpresa.equalsIgnoreCase("null") ) && (!idEmpresa.isEmpty())){
            Empresa empresaFilter = Empresa.findById(Long.parseLong(idEmpresa));
            predicates.add(builder.equal(root.get("empresa"),empresaFilter ));
        }
        if(!(fornecedor.equalsIgnoreCase("null") ) && (!fornecedor.isEmpty()) ){
            predicates.add(builder.like(root.get("fornecedor"), fornecedor+"%"));
        }
        if(!(classificacao.equalsIgnoreCase("null") ) && (!classificacao.isEmpty())){
            predicates.add(builder.equal(root.get("classificacaoDespesa"), classificacao));
        }
        if(!(subClassificacao.equalsIgnoreCase("null") ) && (!subClassificacao.isEmpty())){
            predicates.add(builder.equal(root.get("subClassificacaoDespesa"), subClassificacao));
        }

        List<Order> ordenacao = new ArrayList<Order>();
        ordenacao.add(builder.asc(root.get("dataVencimento")));
        ordenacao.add(builder.asc(root.get("fornecedor")));

        criteria.orderBy(ordenacao);

        criteria.where(predicates.toArray(new Predicate[0]));

        TypedQuery<ContasPagar> query = manager.createQuery(criteria);
        return query.getResultList();
    }
    public List<ContasPagar> findByAllAvancadissimo(FiltroContasPagar filtro) throws ParseException {

        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<ContasPagar> criteria = builder.createQuery(ContasPagar.class);
        var root = criteria.from(ContasPagar.class);

        var predicates = new ArrayList<Predicate>();

        if(filtro.getDataVencimentoFinal() != null){
            predicates.add(builder.between(root.get("dataVencimento"), LocalDate.parse(filtro.getDataVencimentoFinal()) ,LocalDate.parse(filtro.getDataVencimentoFinal())));
        }else if(filtro.getDataVencimentoInicial() != null){
            predicates.add(builder.equal(root.get("dataVencimento"), LocalDate.parse(filtro.getDataVencimentoInicial())));
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


        if(filtro.getClassificacaoDespesa() != null && (!(filtro.getClassificacaoDespesa().isEmpty()))){
            for(int i = 0; i < filtro.getClassificacaoDespesa().size() ; i++){
                if(!(filtro.getClassificacaoDespesa().get(i).equalsIgnoreCase("null") ) && (!filtro.getClassificacaoDespesa().get(i).isEmpty())){
                    predicates.add(builder.equal(root.get("classificacaoDespesa"), filtro.getClassificacaoDespesa().get(i)));
                }
            }
        }
       if( (filtro.getSubClassificacaoDespesa() != null && !(filtro.getSubClassificacaoDespesa().isEmpty()))){
           for(int i = 0; i < filtro.getSubClassificacaoDespesa().size(); i++){
               if(!(filtro.getSubClassificacaoDespesa().get(i).equalsIgnoreCase("null") ) && (!filtro.getSubClassificacaoDespesa().get(i).isEmpty())){
                   predicates.add(builder.equal(root.get("subClassificacaoDespesa"), filtro.getSubClassificacaoDespesa().get(i)));
               }
           }
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
