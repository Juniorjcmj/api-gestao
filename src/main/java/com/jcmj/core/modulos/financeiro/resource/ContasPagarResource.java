package com.jcmj.core.modulos.financeiro.resource;

import com.jcmj.core.modulos.conciliacao.model.ConciliacaoCartoes;
import com.jcmj.core.modulos.conciliacao.resource.input.ConciliacaoCartaoDTO;
import com.jcmj.core.modulos.empresa.model.Empresa;
import com.jcmj.core.modulos.financeiro.model.ContasPagar;
import com.jcmj.core.modulos.financeiro.model.FiltroContasPagar;
import com.jcmj.core.modulos.financeiro.model.dto.ContasPagarDTO;
import com.jcmj.core.modulos.financeiro.model.input.ContasPagarInput;
import com.jcmj.core.modulos.financeiro.model.input.ContasPagarUpdateGenerico;
import com.jcmj.core.modulos.financeiro.model.mapper.ContasPagarMapper;
import com.jcmj.core.modulos.financeiro.repository.ContasPagarRepository;
import com.jcmj.core.modulos.financeiro.service.ContasPagarService;
import io.quarkus.security.Authenticated;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Path("/V1/api-contas-pagar")
@Authenticated
public class ContasPagarResource {

    @Inject
    ContasPagarRepository repository;
    @Inject
    ContasPagarService service;
    @Inject
    ContasPagarMapper mapper;

    //OPERAÇÕES DE CRUD
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response newContasPagar(@Valid ContasPagarInput pagarInput){
        try {
            List<ContasPagarDTO> listDto = service.insertComParcelas(mapper.toModel(pagarInput))
                    .stream()
                    .map(contas -> mapper.daoToDto(contas))
                    .collect(Collectors.toList());
            return Response.ok(listDto).build();

        } catch (Exception e) {
            return Response.status(Response.Status.NOT_IMPLEMENTED).entity(e.getMessage()).build();
        }
    }
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateContasPagar(@Valid ContasPagarInput pagarInput){
        try {
            List<ContasPagarDTO> listDto = service.insertComParcelas(mapper.toModel(pagarInput))
                    .stream()
                    .map(contas -> mapper.daoToDto(contas))
                    .collect(Collectors.toList());
            return Response.ok(listDto).build();

        } catch (Exception e) {
            return Response.status(Response.Status.NOT_IMPLEMENTED).entity(e.getMessage()).build();
        }
    }
    @Transactional
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@QueryParam("id") Long id) {
        try {
            ContasPagar.deleteById(id);
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    @PUT
    @Path("/update-data-pagamento")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateDataPagamento(ContasPagarUpdateGenerico updateGenerico){
       ContasPagar contasUpdate = ContasPagar.findById(updateGenerico.getId());
       contasUpdate.dataPagamento = updateGenerico.getDataPagamento();
        try {
            List<ContasPagarDTO> listDto = service.insertComParcelas(contasUpdate)
                    .stream()
                    .map(contas -> mapper.daoToDto(contas))
                    .collect(Collectors.toList());
            return Response.ok(listDto).build();

        } catch (Exception e) {
            return Response.status(Response.Status.NOT_IMPLEMENTED).entity(e.getMessage()).build();
        }
    }
    @PUT
    @Path("/update-juros-multa")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateJurosMulta(ContasPagarUpdateGenerico updateGenerico){
        ContasPagar contasUpdate = ContasPagar.findById(updateGenerico.getId());
        contasUpdate.jurosMulta = new BigDecimal(updateGenerico.getJurosMulta().replace(".", "").replace(",","."));
        try {
            List<ContasPagarDTO> listDto = service.insertComParcelas(contasUpdate)
                    .stream()
                    .map(contas -> mapper.daoToDto(contas))
                    .collect(Collectors.toList());
            return Response.ok(listDto).build();

        } catch (Exception e) {
            return Response.status(Response.Status.NOT_IMPLEMENTED).entity(e.getMessage()).build();
        }
    }
    @PUT
    @Path("/update-desconto")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateDesconto(ContasPagarUpdateGenerico updateGenerico){
        ContasPagar contasUpdate = ContasPagar.findById(updateGenerico.getId());
        contasUpdate.desconto = new BigDecimal(updateGenerico.getDesconto().replace(".", "").replace(",","."));
        try {
            List<ContasPagarDTO> listDto = service.insertComParcelas(contasUpdate)
                    .stream()
                    .map(contas -> mapper.daoToDto(contas))
                    .collect(Collectors.toList());
            return Response.ok(listDto).build();

        } catch (Exception e) {
            return Response.status(Response.Status.NOT_IMPLEMENTED).entity(e.getMessage()).build();
        }
    }
    @PUT
    @Path("/update-local-pagamento")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateLocalPagamento(ContasPagarUpdateGenerico updateGenerico){
        ContasPagar contasUpdate = ContasPagar.findById(updateGenerico.getId());
        contasUpdate.localPagamento = updateGenerico.getLocalPagamento();
        try {
            List<ContasPagarDTO> listDto = service.insertComParcelas(contasUpdate)
                    .stream()
                    .map(contas -> mapper.daoToDto(contas))
                    .collect(Collectors.toList());
            return Response.ok(listDto).build();

        } catch (Exception e) {
            return Response.status(Response.Status.NOT_IMPLEMENTED).entity(e.getMessage()).build();
        }
    }

    //FILTROS
    @GET
    @Path("/filtro-avancado")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response findByAllAvancado(@QueryParam("dtInicio") String dtInicio,
                                      @QueryParam("dtFim") String dtFim,
                                      @QueryParam("tipoDespesa" )String tipoDespesa,
                                      @QueryParam("classificacao" )String classificacao,
                                      @QueryParam("subclassificacao" )String subclassificacao,
                                      @QueryParam("idEmpresa" )String nomeEmpresa,
                                      @QueryParam("situacao" )String situacao,
                                      @QueryParam("formaPagamento" )String formaPagamento,
                                      @QueryParam("fornecedor" )String fornecedor,
                                      @QueryParam("numeroDocumento" )String numeroDocumento) throws ParseException {
        List<ContasPagarDTO> listDto = repository.findByAllAvancado(dtInicio, dtFim, tipoDespesa, nomeEmpresa,
                        situacao,formaPagamento,fornecedor, classificacao, subclassificacao, numeroDocumento)
                .stream()
                .map(contas -> mapper.daoToDto(contas))
                .collect(Collectors.toList());
        return Response.status(Response.Status.OK)
                .entity(listDto)
                .build();
    }
    @POST
    @Path("/filtro-avancadissimo")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response findByAllAvancadoMiasQueAvancado(FiltroContasPagar filtro) throws ParseException {
        List<ContasPagarDTO> listDto = repository.findByAllAvancadissimo(filtro)
                .stream()
                .map(contas -> mapper.daoToDto(contas))
                .collect(Collectors.toList());
        return Response.status(Response.Status.OK)
                .entity(listDto)
                .build();
    }


    /**
     * Método responsável por filtrar contas a pagar por data de pagamento
     * @param fornecedor
     * @return List<ContasPagarDto>
     */
    @Path("/fornecedor")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByAllFornecedor(@QueryParam("fornecedor") String fornecedor){

       List<ContasPagarDTO> listDto = repository.findByFornecedor(fornecedor)
                          .stream()
                          .map(contas -> mapper.daoToDto(contas))
                          .collect(Collectors.toList());
       return  Response.status(Response.Status.OK)
               .entity(listDto)
               .build();
    }
    /**
     * Método responsável por filtrar contas a pagar por Numero de documento
     * @param numeroDocumento
     * @return List<ContasPagarContasPagar>
     */
    @GET
    @Path("/numero-documento")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByNumeroDocumento(@QueryParam("numeroDocumento") String numeroDocumento ){
        List<ContasPagarDTO> listDto =  repository.findByNumeroDocumento(numeroDocumento)
                .stream()
                .map(contas -> mapper.daoToDto(contas))
                .collect(Collectors.toList());
        return Response.ok(listDto)
                .build();
    }

    /**
     * Método responsável por filtrar contas a pagar por data de vencimento
     * @param dtVencimento
     * @return List<ContasPagarContasPagar>
     */
    @GET
    @Path("/data-vencimento")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response findByDataVencimento(@QueryParam("dtVencimento") String dtVencimento ){

        List<ContasPagarDTO> listDto = repository.findByDataVenvimento(LocalDate.parse(dtVencimento))
                .stream()
                .map(contas -> mapper.daoToDto(contas))
                .collect(Collectors.toList());
        return Response.status(Response.Status.OK)
                .entity(listDto)
                .build();
    }
    /**
     * Método responsável por filtrar contas a pagar por data de pagamento
     * @param dtPagamento
     * @return List<ContasPagarContasPagar>
     */
    @GET
    @Path("/data-pagamento")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response findByDataPagamento(@QueryParam("dtPagamento") String dtPagamento ){

        List<ContasPagarDTO> listDto = repository.findByDataPagamento(LocalDate.parse(dtPagamento))
                .stream()
                .map(contas -> mapper.daoToDto(contas))
                .collect(Collectors.toList());
        return Response.status(Response.Status.OK)
                .entity(listDto)
                .build();
    }

    /**
     * Método responsável por filtrar contas a pagar por periodo de data vencimento
     * @param dtInicio
     * @param dtFim
     * @return lista de contasPagasDTO por periodo de vencimento
     */
    @GET
    @Path("/periodo-vencimento")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response findByPeriodoVencimento(@QueryParam("dtInicio") String dtInicio, @QueryParam("dtFim") String dtFim ){
        List<ContasPagarDTO> listDto = repository.findByPeriodoVencimento(LocalDate.parse(dtInicio), LocalDate.parse(dtFim))
                .stream()
                .map(contas -> mapper.daoToDto(contas))
                .collect(Collectors.toList());
        return Response.status(Response.Status.OK)
                .entity(listDto)
                .build();
    }
    /**
     * Método responsável por filtrar contas a pagar por periodo de data pagamento
     * @param dtInicio
     * @param dtFim
     * @return lista de contasPagasDTO por periodo de pagamento
     */
    @GET
    @Path("/periodo-pagamento")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response findByPeriodoPagamento(@QueryParam("dtInicio") String dtInicio, @QueryParam("dtFim") String dtFim ){
        List<ContasPagarDTO> listDto = repository.findByPeriodoPagamento(LocalDate.parse(dtInicio), LocalDate.parse(dtFim))
                .stream()
                .map(contas -> mapper.daoToDto(contas))
                .collect(Collectors.toList());
        return Response.status(Response.Status.OK)
                .entity(listDto)
                .build();
    }

    /**
     * Método responsável por filtrar contas a pagar por periodo de data vencimento e fornecedor
     * @param dtInicio
     * @param dtFim
     * @param fornecedor
     * @return lista de contasPagasDTO por periodo de vencimento
     */
    @GET
    @Path("/periodo-vencimento-fornecedor")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response findByPeriodoVencimentoFornecedor(@QueryParam("dtInicio") String dtInicio,
                                                      @QueryParam("dtFim") String dtFim,
                                                      @QueryParam("fornecedor") String fornecedor){
        List<ContasPagarDTO> listDto = repository.findByPeriodoVencimentoFornecedor(LocalDate.parse(dtInicio), LocalDate.parse(dtFim), fornecedor)
                .stream()
                .map(contas -> mapper.daoToDto(contas))
                .collect(Collectors.toList());
        return Response.status(Response.Status.OK)
                .entity(listDto)
                .build();
    }
    /**
     * Método responsável por filtrar contas a pagar por periodo de data pagamento e fornecedor
     * @param dtInicio
     * @param dtFim
     * @param fornecedor
     * @return lista de contasPagasDTO por periodo de pagamento
     */
    @GET
    @Path("/periodo-pagamento-fornecedor")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response findByPeriodoPagamentoFornecedor(@QueryParam("dtInicio") String dtInicio,
                                                      @QueryParam("dtFim") String dtFim,
                                                      @QueryParam("fornecedor") String fornecedor){
        List<ContasPagarDTO> listDto = repository.findByPeriodoPagamentoFornecedor(LocalDate.parse(dtInicio), LocalDate.parse(dtFim), fornecedor)
                .stream()
                .map(contas -> mapper.daoToDto(contas))
                .collect(Collectors.toList());
        return Response.status(Response.Status.OK)
                .entity(listDto)
                .build();
    }

    /**
     * Método responsável por filtrar o periodo de vencimento e empresa
     * @param dtInicio
     * @param dtFim
     * @param idEmpresa
     * @return lista de contasPagarDto periodo de vencimento por empresa
     */
    @GET
    @Path("/periodo-vencimento-empresa")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response findByPeriodoEmpresa(@QueryParam("dtInicio") String dtInicio,
                                                     @QueryParam("dtFim") String dtFim,
                                                     @QueryParam("idEmpresa") Long idEmpresa){
        List<ContasPagarDTO> listDto = repository.findByPeriodoVencimentoEmpresa(
                        LocalDate.parse(dtInicio),LocalDate.parse(dtFim), Empresa.findById(idEmpresa))
                .stream()
                .map(contas -> mapper.daoToDto(contas))
                .collect(Collectors.toList());
        return Response.status(Response.Status.OK)
                .entity(listDto)
                .build();
    }
    @GET
    @Path("/periodo-vencimento-classificacao")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response findByPeriodoVenvimentoClassificacao(@QueryParam("dtInicio") String dtInicio,
                                         @QueryParam("dtFim") String dtFim,
                                         @QueryParam("classificacao") String classificacao){
        List<ContasPagarDTO> listDto = repository.findByPeriodoVencimentoClassificacao(
                        LocalDate.parse(dtInicio),LocalDate.parse(dtFim), classificacao)
                .stream()
                .map(contas -> mapper.daoToDto(contas))
                .collect(Collectors.toList());
        return Response.status(Response.Status.OK)
                .entity(listDto)
                .build();
    }
    @GET
    @Path("/periodo-pagamento-classificacao")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response findByPeriodoPagamentoClassificacao(@QueryParam("dtInicio") String dtInicio,
                                                        @QueryParam("dtFim") String dtFim,
                                                        @QueryParam("classificacao") String classificacao){
        List<ContasPagarDTO> listDto = repository.findByPeriodoPagamentoClassificacao(
                        LocalDate.parse(dtInicio),LocalDate.parse(dtFim), classificacao)
                .stream()
                .map(contas -> mapper.daoToDto(contas))
                .collect(Collectors.toList());
        return Response.status(Response.Status.OK)
                .entity(listDto)
                .build();
    }
    @GET
    @Path("/periodo-vencimento-subclassificacao")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response findByPeriodoVenvimentoSubClassificacao(@QueryParam("dtInicio") String dtInicio,
                                                         @QueryParam("dtFim") String dtFim,
                                                         @QueryParam("subclassificacao") String subclassificacao){
        List<ContasPagarDTO> listDto = repository.findByPeriodoVencimentoSubClassificacao(
                        LocalDate.parse(dtInicio),LocalDate.parse(dtFim), subclassificacao)
                .stream()
                .map(contas -> mapper.daoToDto(contas))
                .collect(Collectors.toList());
        return Response.status(Response.Status.OK)
                .entity(listDto)
                .build();
    }
    @GET
    @Path("/periodo-pagamento-subclassificacao")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response findByPeriodoPagamentoSubClassificacao(@QueryParam("dtInicio") String dtInicio,
                                                            @QueryParam("dtFim") String dtFim,
                                                            @QueryParam("subclassificacao") String subclassificacao){
        List<ContasPagarDTO> listDto = repository.findByPeriodoPagamentoSubClassificacao(
                        LocalDate.parse(dtInicio),LocalDate.parse(dtFim), subclassificacao)
                .stream()
                .map(contas -> mapper.daoToDto(contas))
                .collect(Collectors.toList());
        return Response.status(Response.Status.OK)
                .entity(listDto)
                .build();
    }


}
