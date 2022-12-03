package com.jcmj.core.modulos.conciliacao.resource;

import com.jcmj.core.modulos.conciliacao.resource.input.ConciliacaoCartaoInput;
import com.jcmj.core.modulos.conciliacao.resource.mistica.ConciliacaoCartaoMistica;
import com.jcmj.core.modulos.conciliacao.service.ConciliacaoCartaoService;
import com.jcmj.core.modulos.conciliacao.model.ConciliacaoCartoes;
import com.jcmj.core.modulos.empresa.model.Empresa;
import io.quarkus.panache.common.Page;
import io.quarkus.security.Authenticated;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;


@Path("/V1/api-conciliacao")
@Authenticated
public class ConciliacaoCartaoResource {

    @Inject
    ConciliacaoCartaoMistica cartaoMistica;
    @Inject
    ConciliacaoCartaoService service;

    /**
     * Retorna uma Conciliação
     * @param id
     * @return ConciliacaoCatoes
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@QueryParam("id") Long id){
        return Response.status(Response.Status.OK)
                .entity( cartaoMistica.modelToDto(ConciliacaoCartoes.findById(id)))
                .build();

    }

    /**
     * Método responsávvel por retornar uma lista de Conciliações de catão já recebidos
     * @param page
     * @param size
     * @return List<ConciliacaoCartoes>
     */
    @GET
    @Path("/recebidos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllRecebidos(@QueryParam("page") Integer page,@QueryParam("size") Integer size ){
        Page pageable = Page.of(page,size);
        return Response.status(Response.Status.OK)
                .entity(cartaoMistica.listDto(ConciliacaoCartoes.findByAllPageRecebidos(pageable)))
                .build();
    }
    /**
     * Método responsávvel por retornar uma Conciliação de catão
     * @param numeroPedido
     * @return ConciliacaoCartoes
     */
    @GET
    @Path("/numero-pedido")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByNumeroPedido(@QueryParam("numeroPedido") String numeroPedido ){
        return Response.status(Response.Status.OK)
                .entity(cartaoMistica.listDto(ConciliacaoCartoes.findByNumeroPedido(numeroPedido)))
                .build();
    }
    /**
     * Método responsávvel por retornar uma Conciliação de catão
     * @param numeroAute
     * @return ConciliacaoCartoes
     */
    @GET
    @Path("/numero-aute")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByAute(@QueryParam("numeroAute") String numeroAute ){
        return Response.status(Response.Status.OK)
                .entity(cartaoMistica.listDto(ConciliacaoCartoes.findByAute(numeroAute)))
                .build();
    }
    /**
     * Método responsávvel por retornar uma lista de Conciliações de catão não recebidos
     * @param page
     * @return List<ConciliacaoCartoes>
     */
    @GET
    @Path("/nao-recebidos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllNaoRecebidos(@QueryParam("page") Integer page,@QueryParam("size") Integer size ){
        Page pageable = Page.of(page,size);
        return Response.status(Response.Status.OK)
                .entity(cartaoMistica.listDto(ConciliacaoCartoes.findByAllPageNaoRecebidos(pageable)))
                .build();

    }

    /**
     * Método responsável por criar uma nova Conciliação de cartão
     * @param input
     * @return ConciliacaoCartaoDto
     */
    @Transactional
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response newConciliacao(@Valid ConciliacaoCartaoInput input) {
        try {
            ConciliacaoCartoes model = cartaoMistica.inputToModel(input, new ConciliacaoCartoes());
            return Response.status(Response.Status.CREATED)
                    .entity(cartaoMistica.listDto(this.service.persist(model)))
                    .build();

        } catch (Exception e) {
            return Response.status(Response.Status.NOT_IMPLEMENTED).entity(e.getMessage()).build();
        }
    }

    /**
     * Método responsável por alterar uma conciliação de cartão
     * @param input
     * @return ConciliacaoCartaoDto
     */
    @Transactional
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@Valid ConciliacaoCartaoInput input) {
        ConciliacaoCartoes model =  ConciliacaoCartoes.findById(input.getId());
        ConciliacaoCartoes ac =     cartaoMistica.inputToModel(input, model);
        try {
            return Response.status(Response.Status.CREATED)
                    .entity(cartaoMistica.listDto(this.service.persist(ac)))
                    .build();

        } catch (Exception e) {
            return Response.status(Response.Status.NOT_IMPLEMENTED)
                    .entity(e.getMessage())
                    .build();
        }
    }

    /**
     * Método responsável por alterar se foi conferido ou não
     * @param id
     * @param valor
     * @return List<ConciliacoesDto>
     */
    @Path("/alterar-conferido")
    @Transactional
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateConferido(@QueryParam("id") Long id,
                                    @QueryParam("valor") boolean valor,
                                    @QueryParam("quemConferiu") String quemConferiu) {

        try {
            ConciliacaoCartoes model =  ConciliacaoCartoes.findById(id);
            model.foiConferido = valor;
            model.quemConferiu = quemConferiu;
            return Response.status(Response.Status.CREATED)
                    .entity(cartaoMistica.listDto(this.service.persist(model)))
                    .build();

        } catch (Exception e) {
            return Response.status(Response.Status.NOT_IMPLEMENTED)
                    .entity(e.getMessage())
                    .build();
        }
    }

    /**
     * Método responsável por cadastrar uma data de recebimento
     * @param input
     * @return ConciliacaoCartaoDto
     */
    @Path("/data-recebimento")
    @Transactional
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDataRecebimento(@Valid ConciliacaoCartaoInput input) {
        try {
            ConciliacaoCartoes banco = ConciliacaoCartoes.findById(input.getId());
            banco.dataRecebimento = input.getDataRecebimento();
            banco.isRecebido = true;
           return Response.status(Response.Status.CREATED)
                    .entity(cartaoMistica.listDto(this.service.persist(banco)))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_IMPLEMENTED)
                    .entity(e.getMessage())
                    .build();
        }
    }

    /**
     * Método responsável por excluir uma conciliação de cartão
     * @param id
     * @return void
     */
    @Transactional
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@QueryParam("id") Long id) {
        try {
            ConciliacaoCartoes.deleteById(id);
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Método responsável por filtrar por empresa
     * @param id
     * @return List<ConciliacoesDto>
     */
    @Path("/find-empresa")
    @Transactional
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response findEmpresa(@QueryParam("id") Long id) {
        try {
           Empresa empresa = Empresa.findById(id);
            return Response.status(Response.Status.CREATED)
                    .entity(cartaoMistica.listDto(ConciliacaoCartoes.findByAllEmpresa(empresa)))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_IMPLEMENTED)
                    .entity(e.getMessage())
                    .build();
        }
    }

    /**
     * Método responsável por filtrar por empresa
     * @param data
     * @return List<ConciliacoesDto>
     */
    @Path("/find-data")
    @Transactional
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response findData(@QueryParam("data") String data) {
        try {
             return Response.status(Response.Status.CREATED)
                    .entity(cartaoMistica.listDto(ConciliacaoCartoes.findByAllData(LocalDate.parse(data))))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_IMPLEMENTED)
                    .entity(e.getMessage())
                    .build();
        }
    }


}
