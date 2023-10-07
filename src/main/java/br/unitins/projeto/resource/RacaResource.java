package br.unitins.projeto.resource;

import br.unitins.projeto.application.Result;
import br.unitins.projeto.dto.raca.RacaDTO;
import br.unitins.projeto.dto.raca.RacaResponseDTO;
import br.unitins.projeto.service.raca.RacaService;
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

@Path("/racas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RacaResource {

    @Inject
    RacaService service;

    private static final Logger LOG = Logger.getLogger(RacaResource.class);

    @GET
    public List<RacaResponseDTO> getAll() {
        LOG.info("Buscando todos os raças.");
        return service.getAll();
    }

    @GET
    @Path("/paginado")
    public List<RacaResponseDTO> getAllPaginado(
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
    public RacaResponseDTO findById(@PathParam("id") Long id) {
        LOG.info("Buscando um raças pelo id.");
        return service.findById(id);
    }

    @POST
//    @RolesAllowed({"Admin"})
    public Response insert(RacaDTO dto) {
        LOG.infof("Inserindo um raças: %s", dto.nome());
        Result result = null;

        try {
            RacaResponseDTO response = service.create(dto);
            LOG.infof("Raça (%d) criado com sucesso.", response.id());
            return Response.status(Status.CREATED).entity(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir um raças.");
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
    public Response update(@PathParam("id") Long id, RacaDTO dto) {
        LOG.infof("Alterando um raças: %s", dto.nome());
        Result result = null;

        try {
            RacaResponseDTO response = service.update(id, dto);
            LOG.infof("Raça (%d) alterado com sucesso.", response.id());
            return Response.ok(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao alterar um raça.");
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
//    @RolesAllowed({"Admin"})
    public Response alterarSituacao(@PathParam("id") Long id, Boolean situacao) {
        LOG.infof("Alterando situação da raça");
        Result result = null;

        try {
            RacaResponseDTO response = service.alterarSituacao(id, situacao);
            LOG.infof("Raça (%d) alterado com sucesso.", response.id());
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
//    @RolesAllowed({"Admin"})
    public Response delete(@PathParam("id") Long id) {
        LOG.infof("Deletando um raças: %s", id);
        Result result = null;

        try {
            service.delete(id);
            LOG.infof("Raça (%d) deletado com sucesso.", id);
            return Response.status(Status.NO_CONTENT).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao deletar um raça.");
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
        LOG.infof("Pesquisando raças pelo nome: %s", nome);
        Result result = null;

        try {
            List<RacaResponseDTO> response = service.findByNome(nome, situacao, pageNumber, pageSize);
            LOG.infof("Pesquisa realizada com sucesso.");
            return Response.ok(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao pesquisar raças.");
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
        return service.countByNome(nome, situacao);
    }

}

