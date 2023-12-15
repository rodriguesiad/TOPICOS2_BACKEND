package br.unitins.projeto.resource;

import br.unitins.projeto.application.Result;
import br.unitins.projeto.dto.municipio.MunicipioDTO;
import br.unitins.projeto.dto.municipio.MunicipioResponseDTO;
import br.unitins.projeto.service.municipio.MunicipioService;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
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

@Path("/municipios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MunicipioResource {

    @Inject
    MunicipioService service;

    private static final Logger LOG = Logger.getLogger(MunicipioResource.class);

    @GET
    public List<MunicipioResponseDTO> getAll(@QueryParam("page") @DefaultValue("0") int page,
                                             @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        LOG.info("Buscando todos os municipios.");
        return service.getAll(page, pageSize);
    }

    @GET
    @Path("/{id}")
    public MunicipioResponseDTO findById(@PathParam("id") Long id) {
        LOG.info("Buscando um município pelo id.");
        return service.findById(id);
    }

    @POST
//    @RolesAllowed({"Admin"})
    public Response insert(MunicipioDTO dto) {
        LOG.infof("Inserindo um municipio: %s", dto.nome());
        Result result = null;

        try {
            MunicipioResponseDTO response = service.create(dto);
            LOG.infof("Município (%d) criado com sucesso.", response.id());
            return Response.status(Status.CREATED).entity(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir um municipio.");
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
    public Response update(@PathParam("id") Long id, MunicipioDTO dto) {
        LOG.infof("Alterando um municipio: %s", dto.nome());
        Result result = null;

        try {
            MunicipioResponseDTO response = service.update(id, dto);
            LOG.infof("Município (%d) alterado com sucesso.", response.id());
            return Response.ok(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao alterar um município.");
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
        LOG.infof("Deletando um municipio: %s", id);
        Result result = null;

        try {
            service.delete(id);
            LOG.infof("Município (%d) deletado com sucesso.", id);
            return Response.status(Status.NO_CONTENT).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao deletar um município.");
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
    public Response search(@PathParam("nome") String nome,
                           @QueryParam("page") @DefaultValue("0") int page,
                           @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        LOG.infof("Pesquisando municípios pelo nome: %s", nome);
        Result result = null;

        try {
            List<MunicipioResponseDTO> response = service.findByNome(nome, page, pageSize);
            LOG.infof("Pesquisa realizada com sucesso.");
            return Response.ok(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao pesquisar municípios.");
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
    public long count(){
        return service.count();
    }

    @GET
    @Path("/search/{nome}/count")
    public long count(@PathParam("nome") String nome) {
        return service.countByNome(nome);
    }

    @GET
    @Path("/estado/{idEstado}")
    public List<MunicipioResponseDTO> getByEstado(@PathParam("idEstado") Long idEstado) {
        return service.findByEstado(idEstado);
    }

}

