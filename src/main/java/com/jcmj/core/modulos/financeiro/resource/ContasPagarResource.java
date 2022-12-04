package com.jcmj.core.modulos.financeiro.resource;

import com.jcmj.core.modulos.conciliacao.model.ConciliacaoCartoes;
import com.jcmj.core.modulos.conciliacao.resource.input.ConciliacaoCartaoDTO;
import com.jcmj.core.modulos.empresa.model.Empresa;
import com.jcmj.core.modulos.financeiro.model.ContasPagar;
import com.jcmj.core.modulos.financeiro.model.dto.ContasPagarDTO;
import com.jcmj.core.modulos.financeiro.model.input.ContasPagarInput;
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
//
//    /**
//     * Método responsável por filtrar contas a pagar por data de vencimento
//     * @param dtVencimento
//     * @return List<ContasPagarContasPagar>
//     */
//    @GET
//    @Path("/data-vencimento")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response findByDataVencimento(@QueryParam("dtVencimento") LocalDate dtVencimento ){
//        return Response.status(Response.Status.OK)
//                .entity(repository.findByDataVenvimento(dtVencimento))
//                .build();
//    }
//    /**
//     * Método responsável por filtrar contas a pagar por data de pagamento
//     * @param dtPagamento
//     * @return List<ContasPagarContasPagar>
//     */
//    @GET
//    @Path("/data-pagamento")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response findByDataPagamento(@QueryParam("dtPagamento") LocalDate dtPagamento ){
//        return Response.status(Response.Status.OK)
//                .entity(repository.findByDataPagamento(dtPagamento))
//                .build();
//    }


}
