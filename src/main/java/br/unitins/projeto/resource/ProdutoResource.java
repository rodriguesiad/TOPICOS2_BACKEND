package br.unitins.projeto.resource;

import br.unitins.projeto.application.Result;
import br.unitins.projeto.dto.produto.ProdutoDTO;
import br.unitins.projeto.dto.produto.ProdutoResponseDTO;
import br.unitins.projeto.service.produto.ProdutoService;
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

@Path("/produtos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProdutoResource {

    @Inject
    ProdutoService service;

    private static final Logger LOG = Logger.getLogger(ProdutoResource.class);

    @GET
    public List<ProdutoResponseDTO> getAll() {
        LOG.info("Buscando todos os produtos.");
        return service.getAll();
    }

    @GET
    @Path("/{id}")
    public ProdutoResponseDTO findById(@PathParam("id") Long id) {
        LOG.info("Buscando um produtos pelo id.");
        return service.findById(id);
    }

    @POST
//    @RolesAllowed({"Admin"})
    public Response insert(ProdutoDTO dto) {
        LOG.infof("Inserindo um produtos: %s", dto.nome());
        Result result = null;

        try {
            ProdutoResponseDTO response = service.create(dto);
            LOG.infof("Produto (%d) criado com sucesso.", response.id());
            return Response.status(Status.CREATED).entity(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir um produtos.");
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
    public Response update(@PathParam("id") Long id, ProdutoDTO dto) {
        LOG.infof("Alterando um produtos: %s", dto.nome());
        Result result = null;

        try {
            ProdutoResponseDTO response = service.update(id, dto);
            LOG.infof("Produto (%d) alterado com sucesso.", response.id());
            return Response.ok(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao alterar um produto.");
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
        LOG.infof("Deletando um produtos: %s", id);
        Result result = null;

        try {
            service.delete(id);
            LOG.infof("Produto (%d) deletado com sucesso.", id);
            return Response.status(Status.NO_CONTENT).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao deletar um produto.");
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
                           @QueryParam("ativo") Boolean ativo) {
        LOG.infof("Pesquisando produtos pelo nome: %s", nome);
        Result result = null;

        try {
            List<ProdutoResponseDTO> response = service.findByNome(nome, ativo, pageNumber, pageSize);
            LOG.infof("Pesquisa realizada com sucesso.");
            return Response.ok(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao pesquisar produtos.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }

        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

    @GET
    @Path("/count")
    public Long count() {
        return service.count();
    }

    @GET
    @Path("/paginado")
    public List<ProdutoResponseDTO> getAllPaginado(
            @QueryParam("page") int pageNumber,
            @QueryParam("size") int pageSize
    ) {
        return service.findAllPaginado(pageNumber, pageSize);
    }

}

