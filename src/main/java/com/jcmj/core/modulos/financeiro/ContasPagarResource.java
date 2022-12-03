package com.jcmj.core.modulos.financeiro;

import com.jcmj.core.modulos.conciliacao.model.ConciliacaoCartoes;
import com.jcmj.core.modulos.empresa.model.Empresa;
import com.jcmj.core.modulos.financeiro.model.ContasPagar;
import io.quarkus.security.Authenticated;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;

@Path("/V1/api-contas-pagar")
@Authenticated
public class ContasPagarResource {

    //OPERAÇÕES DE CRUD
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

    //FILTROS
    /**
     * Método responsável por filtrar contas a pagar por Numero de documento
     * @param numeroDocumento
     * @return List<ContasPagarContasPagar>
     */
    @GET
    @Path("/numero-documento")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByNumeroDocumento(@QueryParam("numeroDocumento") String numeroDocumento ){
        return Response.status(Response.Status.OK)
                .entity(ContasPagar.findByNumeroDocumento(numeroDocumento))
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
    public Response findByDataVencimento(@QueryParam("dtVencimento") LocalDate dtVencimento ){
        return Response.status(Response.Status.OK)
                .entity(ContasPagar.findByDataVenvimento(dtVencimento))
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
    public Response findByDataPagamento(@QueryParam("dtPagamento") LocalDate dtPagamento ){
        return Response.status(Response.Status.OK)
                .entity(ContasPagar.findByDataPagamento(dtPagamento))
                .build();
    }
    /**
     * Método responsável por filtrar contas a pagar por data de pagamento
     * @param fornecedor
     * @return List<ContasPagarContasPagar>
     */
    @GET
    @Path("/fornecedor")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByFornecedor(@QueryParam("fornecedor") String fornecedor ){
        return Response.status(Response.Status.OK)
                .entity(ContasPagar.findByFornecedor(fornecedor))
                .build();
    }
}
