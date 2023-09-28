package br.unitins.projeto.resource;

import br.unitins.projeto.application.Result;
import br.unitins.projeto.dto.categoria.CategoriaDTO;
import br.unitins.projeto.dto.categoria.CategoriaResponseDTO;
import br.unitins.projeto.dto.situacao.AlterarSituacaoDTO;
import br.unitins.projeto.service.categoria.CategoriaService;
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

@Path("/categorias")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CategoriaResource {

    @Inject
    CategoriaService service;

    private static final Logger LOG = Logger.getLogger(CategoriaResource.class);

    @GET
    public List<CategoriaResponseDTO> getAll() {
        LOG.info("Buscando todos os categorias.");
        return service.getAll();
    }

    @GET
    @Path("/paginado")
    public List<CategoriaResponseDTO> getAllPaginado(
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
    public CategoriaResponseDTO findById(@PathParam("id") Long id) {
        LOG.info("Buscando uma categorias pelo id.");
        return service.findById(id);
    }

    @POST
//    @RolesAllowed({"Admin"})
    public Response insert(CategoriaDTO dto) {
        LOG.infof("Inserindo uma categorias: %s", dto.nome());
        Result result = null;

        try {
            CategoriaResponseDTO response = service.create(dto);
            LOG.infof("Categoria (%d) criado com sucesso.", response.id());
            return Response.status(Status.CREATED).entity(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir uma categorias.");
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
    public Response update(@PathParam("id") Long id, CategoriaDTO dto) {
        LOG.infof("Alterando uma categorias: %s", dto.nome());
        Result result = null;

        try {
            CategoriaResponseDTO response = service.update(id, dto);
            LOG.infof("Categoria (%d) alterado com sucesso.", response.id());
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

    @PUT
    @Path("/situacao/{id}")
//    @RolesAllowed({"Admin"})
    public Response alterarSituacao(@PathParam("id") Long id, AlterarSituacaoDTO dto) {
        LOG.infof("Alterando situação da categoria");
        Result result = null;

        try {
            CategoriaResponseDTO response = service.alterarSituacao(id, dto);
            LOG.infof("Categoria (%d) alterado com sucesso.", response.id());
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
        LOG.infof("Deletando uma categorias: %s", id);
        Result result = null;

        try {
            service.delete(id);
            LOG.infof("Categoria (%d) deletado com sucesso.", id);
            return Response.status(Status.NO_CONTENT).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao deletar uma categoria.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }

        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

    @GET
    @Path("/search/{nome}")
    public Response search(@PathParam("nome") String nome) {
        LOG.infof("Pesquisando categorias pelo nome: %s", nome);
        Result result = null;

        try {
            List<CategoriaResponseDTO> response = service.findByNome(nome);
            LOG.infof("Pesquisa realizada com sucesso.");
            return Response.ok(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao pesquisar categorias.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }

        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

}

