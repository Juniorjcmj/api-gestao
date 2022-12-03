package com.jcmj.core.modulos.conciliacao.resource;

import com.jcmj.core.modulos.conciliacao.model.OperadoraCartao;
import io.quarkus.security.Authenticated;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/V1/api-operadora-cartao")
@Authenticated
public class OperadoraCartaoResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<OperadoraCartao> findByAtivas(){
        return OperadoraCartao.findByAtivasOuDesativadas(true);
    }
    @Path("/desativadas")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<OperadoraCartao> findByDesativadas(){
        return OperadoraCartao.findByAtivasOuDesativadas(false);
    }

    @GET
    @Path("/findId")
    @Produces(MediaType.APPLICATION_JSON)
    public OperadoraCartao findById(@QueryParam("id") Long id){
        return OperadoraCartao.findById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response newOperadora(@Valid OperadoraCartao operadoraCartao){

        try {
            operadoraCartao.status = true;
            operadoraCartao.persist();
            return Response.status(Response.Status.CREATED)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_IMPLEMENTED).entity(e.getMessage()).build();
        }
    }
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateOperadora(@Valid OperadoraCartao operadoraCartao){

        try {
            OperadoraCartao model = OperadoraCartao.findById(operadoraCartao.id);
            model.nome = operadoraCartao.nome;
            model.status = operadoraCartao.status;
            model.taxaPadraoCredito = operadoraCartao.taxaPadraoCredito;
            model.taxaAntecipacaoDebito = operadoraCartao.taxaAntecipacaoDebito;
            model.diasParaRecebimento = operadoraCartao.diasParaRecebimento;
            model.antecipacaoAutomatica = operadoraCartao.antecipacaoAutomatica;
            model.taxaPadraoDebito = operadoraCartao.taxaPadraoDebito;
            model.taxaAntecipacaoCredito = operadoraCartao.taxaAntecipacaoCredito;
            model.inicio = operadoraCartao.inicio;
            model.fim = operadoraCartao.fim;

            model.persist();
            return Response.status(Response.Status.OK)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_MODIFIED).entity(e.getMessage()).build();
        }
    }

    @Transactional
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@QueryParam("id") Long id) {
        try {
            OperadoraCartao.deleteById(id);
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @Transactional
    @GET
    @Path("/desativar-operadora")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response desativarOperadora(@QueryParam("id") Long id) {
        try {
           OperadoraCartao model = OperadoraCartao.findById(id);
            model.status = false;
            model.persist();
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }


}
