package br.unitins.projeto.resource;

import br.unitins.projeto.application.Result;
import br.unitins.projeto.dto.especie.EspecieDTO;
import br.unitins.projeto.dto.especie.EspecieResponseDTO;
import br.unitins.projeto.service.especie.EspecieService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.jboss.logging.Logger;

import java.util.List;

@Path("/especies")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EspecieResource {

    @Inject
    EspecieService service;

    private static final Logger LOG = Logger.getLogger(EspecieResource.class);

    @GET
    public List<EspecieResponseDTO> getAll() {
        LOG.info("Buscando todos os especies.");
        return service.getAll();
    }

    @GET
    @Path("/paginado")
    public List<EspecieResponseDTO> getAllPaginado(
            @QueryParam("page") int pageNumber,
            @QueryParam("size") int pageSize
    ) {
        return service.findAllPaginado(pageNumber, pageSize);
    }

    @GET
    @Path("/count")
    public Long count() {
        return service.count();
    }

    @GET
    @Path("/{id}")
    public EspecieResponseDTO findById(@PathParam("id") Long id) {
        LOG.info("Buscando um especies pelo id.");
        return service.findById(id);
    }

    @POST
    @RolesAllowed({"Administrador"})
    public Response insert(EspecieDTO dto) {
        LOG.infof("Inserindo um especies: %s", dto.nome());

        EspecieResponseDTO response = service.create(dto);
        LOG.infof("Espécie (%d) criado com sucesso.", response.id());
        return Response.status(Status.CREATED).entity(response).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"Administrador"})
    public Response update(@PathParam("id") Long id, EspecieDTO dto) {
        LOG.infof("Alterando um especies: %s", dto.nome());
        Result result = null;

        try {
            EspecieResponseDTO response = service.update(id, dto);
            LOG.infof("Espécie (%d) alterado com sucesso.", response.id());
            return Response.ok(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao alterar um especie.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }

        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

    @PUT
    @Path("/situacao/{id}")
    @RolesAllowed({"Administrador"})
    public Response alterarSituacao(@PathParam("id") Long id, Boolean dto) {
        LOG.infof("Alterando situação da espécie");
        Result result = null;

        try {
            EspecieResponseDTO response = service.alterarSituacao(id, dto);
            LOG.infof("Espécie (%d) alterado com sucesso.", response.id());
            return Response.ok(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao alterar uma categoria.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }

        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Administrador"})
    public Response delete(@PathParam("id") Long id) {
        LOG.infof("Deletando um especies: %s", id);
        Result result = null;

        try {
            service.delete(id);
            LOG.infof("Espécie (%d) deletado com sucesso.", id);
            return Response.status(Status.NO_CONTENT).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao deletar um especie.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }

        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

    @GET
    @Path("/search")
    @RolesAllowed({"Administrador", "Comum"})
    public Response search(@QueryParam("page") int pageNumber,
                           @QueryParam("size") int pageSize,
                           @QueryParam("nome") String nome,
                           @QueryParam("ativo") Boolean ativo) {
        LOG.infof("Pesquisando especies pelo nome: %s", nome);
        Result result = null;

        try {
            List<EspecieResponseDTO> response = service.findByNome(nome, ativo, pageNumber, pageSize);
            LOG.infof("Pesquisa realizada com sucesso.");
            return Response.ok(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao pesquisar especies.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }

        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

    @GET
    @Path("/search/count")
    public Long count(@QueryParam("nome") String nome,
                      @QueryParam("ativo") Boolean ativo) {
        return service.countByNome(nome, ativo);
    }

}

