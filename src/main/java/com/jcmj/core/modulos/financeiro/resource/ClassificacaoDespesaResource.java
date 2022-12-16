package com.jcmj.core.modulos.financeiro.resource;

import com.jcmj.core.modulos.financeiro.model.ClassificacaoDespesa;
import com.jcmj.core.modulos.financeiro.model.ContasPagar;
import com.jcmj.core.modulos.financeiro.model.dto.ContasPagarDTO;
import com.jcmj.core.modulos.financeiro.model.input.ContasPagarInput;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.security.Authenticated;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/V1/api-classificacao-despesa")
@Authenticated
public class ClassificacaoDespesaResource {


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response newlassificacao(@Valid ClassificacaoDespesa classificacaoDespesa){
        try {
            classificacaoDespesa.persist();
            return Response.ok(classificacaoDespesa).build();

        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateClassificacao(@Valid ClassificacaoDespesa classificacaoDespesa){
        try {
            ClassificacaoDespesa model = ClassificacaoDespesa.findById(classificacaoDespesa.id);
            model.descricao = classificacaoDespesa.descricao;
            ClassificacaoDespesa.persist(model);
            return Response.ok(classificacaoDespesa).build();

        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    @Transactional
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@QueryParam("id") Long id) {
        try {
            ClassificacaoDespesa.deleteById(id);
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByAllClassificacao(){
      ;
        return  Response.status(Response.Status.OK)
                .entity( ClassificacaoDespesa.findAll().stream().collect(Collectors.toList()))
                .build();
    }
    @Path("buscar-por-id")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByClassificacao(@QueryParam("id") Long id){
        return  Response.status(Response.Status.OK)
                .entity(ClassificacaoDespesa.findById(id))
                .build();
    }
}
