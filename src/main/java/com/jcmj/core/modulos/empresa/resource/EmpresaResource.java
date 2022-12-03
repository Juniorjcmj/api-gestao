package com.jcmj.core.modulos.empresa.resource;

import com.jcmj.core.modulos.empresa.model.Empresa;
import io.quarkus.security.Authenticated;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/V1/api-empresa")
@Authenticated
public class EmpresaResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Empresa> findByAll(){
        return Empresa.listAll();
    }

    @GET
    @Path("/findId")
    @Produces(MediaType.APPLICATION_JSON)
    public Empresa findById(@QueryParam("id") Long id){
        return Empresa.findById(id);
    }
    @GET
    @Path("/find-nome")
    @Produces(MediaType.APPLICATION_JSON)
    public Empresa findByNome(@QueryParam("nome") String nome){
        return Empresa.findByNome(nome);
    }
    @GET
    @Path("/find-cnpj")
    @Produces(MediaType.APPLICATION_JSON)
    public Empresa findByCnpj(@QueryParam("cnpj") String cnpj){
        return Empresa.findBycnpj(cnpj);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response newEmpresa(@Valid Empresa empresa){

        try {
            empresa.persist();
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
    public Response UpdateEmpresa(@Valid Empresa empresa){

        try {
                Empresa model = Empresa.findById(empresa.id);
                model.nome = empresa.nome;
                model.cnpj =empresa.cnpj;
                model.endereco = empresa.endereco;
                model.telFixo = empresa.telFixo;
                model.telMovel = empresa.telMovel;
                model.email = empresa.email;
                model.persist();

            return Response.status(Response.Status.CREATED)
                    .build();
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
            Empresa.deleteById(id);
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }


}
