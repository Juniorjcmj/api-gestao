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












}
