package com.jcmj.core.modulos.financeiro.resource;

import com.jcmj.core.modulos.financeiro.model.ClassificacaoDespesa;
import com.jcmj.core.modulos.financeiro.model.SubClassificacaoDespesa;
import io.quarkus.security.Authenticated;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/V1/api-subclassificacao-despesa")
@Authenticated
public class SubClassificacaoDespesaResource {


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response newSubClassificacao(@QueryParam("idClassificacao") Long idClassificacao,
                                    @QueryParam("descricaoSubClassificacao") String descricaoSubClassificacao){
        try {
            ClassificacaoDespesa classificacao = ClassificacaoDespesa.findById(idClassificacao);
            List<SubClassificacaoDespesa> subClassificacaoDespesa= classificacao.subClassificacao;
            SubClassificacaoDespesa sub = new SubClassificacaoDespesa();
            sub.descricao = descricaoSubClassificacao;
            sub.persist();
            subClassificacaoDespesa.add(sub);
            classificacao.subClassificacao = subClassificacaoDespesa;
            classificacao.persist();
            return Response.ok(classificacao).build();

        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateSubClassificacao(@QueryParam("idSubClassificacao") Long idSubClassificacao,
                                    @QueryParam("descricaoSubClassificacao") String descricaoSubClassificacao){
        try {
            SubClassificacaoDespesa sub = SubClassificacaoDespesa.findById(idSubClassificacao);
            sub.descricao = descricaoSubClassificacao;
            sub.persist();
            return Response.ok(sub).build();

        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    @Path("delete-subclassificacao")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response deleteSubClassificacao(@QueryParam("idClassificacao") Long idClassificacao,
                                        @QueryParam("descricaoSubClassificacao") String descricaoSubClassificacao){
        try {
            ClassificacaoDespesa classificacao = ClassificacaoDespesa.findById(idClassificacao);
           for(int i =0; i < classificacao.subClassificacao.size(); i++){
               if(classificacao.subClassificacao.get(i).descricao.equalsIgnoreCase( descricaoSubClassificacao)){
                   var subclassificacao = classificacao.subClassificacao.get(i);
                   classificacao.subClassificacao.remove(classificacao.subClassificacao.get(i));
                   subclassificacao.delete();
               }
           }
            classificacao.persist();
            return Response.ok(classificacao).build();

        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
