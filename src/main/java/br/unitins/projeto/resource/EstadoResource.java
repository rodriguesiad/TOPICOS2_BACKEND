package br.unitins.projeto.resource;

import br.unitins.projeto.application.Result;
import br.unitins.projeto.dto.estado.EstadoDTO;
import br.unitins.projeto.dto.estado.EstadoResponseDTO;
import br.unitins.projeto.service.estado.EstadoService;
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

@Path("/estados")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EstadoResource {

    @Inject
    EstadoService service;

    private static final Logger LOG = Logger.getLogger(EstadoResource.class);

    @GET
    public List<EstadoResponseDTO> getAll() {
        LOG.info("Buscando todos os estados.");
        return service.getAll();
    }

    @GET
    @Path("/{id}")
    public EstadoResponseDTO findById(@PathParam("id") Long id) {
        LOG.info("Buscando um estados pelo id.");
        return service.findById(id);
    }

    @POST
//    @RolesAllowed({"Admin"})
    public Response insert(EstadoDTO dto) {
        LOG.infof("Inserindo um estados: %s", dto.nome());
        Result result = null;

        try {
            EstadoResponseDTO response = service.create(dto);
            LOG.infof("Estado (%d) criado com sucesso.", response.id());
            return Response.status(Status.CREATED).entity(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir um estados.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }

        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

    @PUT
    @Path("/{id}")
//    @RolesAllowed({"Admin"})
    public Response update(@PathParam("id") Long id, EstadoDTO dto) {
        LOG.infof("Alterando um estados: %s", dto.nome());
        Result result = null;

        try {
            EstadoResponseDTO response = service.update(id, dto);
            LOG.infof("Estado (%d) alterado com sucesso.", response.id());
            return Response.ok(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao alterar um estado.");
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
//    @RolesAllowed({"Admin"})
    public Response delete(@PathParam("id") Long id) {
        LOG.infof("Deletando um estados: %s", id);
        Result result = null;

        try {
            service.delete(id);
            LOG.infof("Estado (%d) deletado com sucesso.", id);
            return Response.status(Status.NO_CONTENT).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao deletar um estado.");
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
    public Response search(@QueryParam("page") int pageNumber,
                           @QueryParam("size") int pageSize,
                           @QueryParam("nome") String nome,
                           @QueryParam("situacao") String situacao) {
        LOG.infof("Pesquisando estados pelo nome: %s", nome);
        Result result = null;

        try {
            List<EstadoResponseDTO> response = service.findByFiltro(nome, situacao, pageNumber, pageSize);
            LOG.infof("Pesquisa realizada com sucesso.");
            return Response.ok(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao pesquisar estados.");
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
                      @QueryParam("situacao") String situacao) {
        return service.countByFiltro(nome, situacao);
    }

    @GET
    @Path("/count")
    public Long count() {
        return service.count();
    }
}

