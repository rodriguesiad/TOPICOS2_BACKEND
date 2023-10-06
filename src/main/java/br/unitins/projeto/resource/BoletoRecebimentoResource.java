package br.unitins.projeto.resource;

import br.unitins.projeto.application.Result;
import br.unitins.projeto.dto.metodo.recebimento.boleto.BoletoRecebimentoDTO;
import br.unitins.projeto.dto.metodo.recebimento.boleto.BoletoRecebimentoResponseDTO;
import br.unitins.projeto.service.metodo_recebimento.boleto.BoletoRecebimentoService;
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
import org.jboss.logging.Logger;

import java.util.List;

@Path("/metodo-recebimento/boleto")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BoletoRecebimentoResource {

    @Inject
    BoletoRecebimentoService service;

    private static final Logger LOG = Logger.getLogger(RacaResource.class);

    @GET
    @Path("/get-inativos")
    public List<BoletoRecebimentoResponseDTO> findByInativo(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize
    ) {
        LOG.info("Buscando métodos de recebimento(boleto) inativos.");
        return service.listAllInativo(page, pageSize);
    }

    @GET
    @Path("/{id}")
    public BoletoRecebimentoResponseDTO findById(@PathParam("id") Long id) {
        LOG.info("Buscando um método de recebimento pelo id.");
        return service.findById(id);
    }

    @GET
    public BoletoRecebimentoResponseDTO findByAtivo() {
        LOG.info("Buscando método de recebimento(boleto) ativo.");
        return service.findByAtivo();
    }

    @POST
//    @RolesAllowed({"Admin"})
    public Response insert(BoletoRecebimentoDTO dto) {
        LOG.infof("Inserindo um método de recebimento do tipo boleto: %s", dto.nome());
        Result result = null;

        try {
            BoletoRecebimentoResponseDTO response = service.create(dto);
            LOG.infof("Método de Recebimento (%d) criado com sucesso.", response.id());
            return Response.status(Response.Status.CREATED).entity(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir um método de recebimento.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }

        return Response.status(Response.Status.NOT_FOUND).entity(result).build();
    }

    @PUT
    @Path("/{id}")
//    @RolesAllowed({"Admin"})
    public Response update(@PathParam("id") Long id, BoletoRecebimentoDTO dto) {
        LOG.infof("Alterando um método de recebimento(pix): %s", dto.nome());
        Result result = null;

        try {
            BoletoRecebimentoResponseDTO response = service.update(id, dto);
            LOG.infof("Pix (%d) alterado com sucesso.", response.id());
            return Response.ok(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao alterar um método de recebimento(pix).");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }

        return Response.status(Response.Status.NOT_FOUND).entity(result).build();
    }

    @DELETE
    @Path("/{id}")
//    @RolesAllowed({"Admin"})
    public Response delete(@PathParam("id") Long id) {
        LOG.infof("Deletando um método de recebimento(pix): %s", id);
        Result result = null;

        try {
            service.delete(id);
            LOG.infof("Pix (%d) deletado com sucesso.", id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao deletar um método de recebimento(pix).");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }

        return Response.status(Response.Status.NOT_FOUND).entity(result).build();
    }

    @GET
    @Path("/count")
    public long count() {
        return service.count();
    }

    @GET
    @Path("/search/{cnpj}/count")
    public long count(@PathParam("cnpj") String cnpj) {
        return service.countByCNPJ(cnpj);
    }

    @GET
    @Path("/search/{cnpj}")
    public List<BoletoRecebimentoResponseDTO> search(
            @PathParam("cnpj") String cnpj,
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        return service.findByCNPJ(cnpj, page, pageSize);

    }

}
