package br.unitins.projeto.resource;

import br.unitins.projeto.application.Result;
import br.unitins.projeto.dto.metodo.pagamento.boleto.BoletoDTO;
import br.unitins.projeto.dto.metodo.pagamento.boleto.BoletoResponseDTO;
import br.unitins.projeto.dto.compra.CompraDTO;
import br.unitins.projeto.dto.compra.CompraResponseDTO;
import br.unitins.projeto.dto.compra.StatusCompraDTO;
import br.unitins.projeto.dto.historico_entrega.HistoricoEntregaDTO;
import br.unitins.projeto.dto.historico_entrega.HistoricoEntregaResponseDTO;
import br.unitins.projeto.dto.metodo.pagamento.pix.PixDTO;
import br.unitins.projeto.dto.metodo.pagamento.pix.PixResponseDTO;
import br.unitins.projeto.dto.usuario.UsuarioResponseDTO;
import br.unitins.projeto.service.compra.CompraService;
import br.unitins.projeto.service.usuario.UsuarioService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import java.util.List;

@Path("/compras")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CompraResource {

    @Inject
    UsuarioService usuarioService;

    @Inject
    CompraService service;

    @Inject
    JsonWebToken jwt;

    private static final Logger LOG = Logger.getLogger(CompraResource.class);

    private Long getIdUsuario() {
        String login = jwt.getSubject();
        UsuarioResponseDTO usuario = usuarioService.findByLogin(login);
        return usuario.id();
    }

    @POST
    @RolesAllowed({"Admin", "User"})
    public Response insert(@Valid CompraDTO dto) {
        LOG.info("Inserindo uma compra");
        Result result = null;

        try {
            CompraResponseDTO response = service.create(dto, getIdUsuario());
            LOG.infof("Compra criada com sucesso.", response.id());
            return Response.status(Status.CREATED).entity(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir uma compra.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }

        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

    @PATCH
    @RolesAllowed({"Admin", "User"})
    @Path("/{idCompra}/alterar-status")
    public Response alterStatus(@PathParam("idCompra") Long id, @Valid StatusCompraDTO dto) {
        LOG.info("Alterando status de uma compra");
        Result result = null;

        try {
            CompraResponseDTO response = service.alterStatusCompra(id, dto);
            LOG.infof("Status alterado com sucesso.", response.id());
            return Response.ok().entity(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao alterar o status de uma compra.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }

        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

    @GET
    @RolesAllowed({"Admin", "User"})
    @Path("/{idCompra}/historico-entrega")
    public Response getHistoricoEntrega(@PathParam("idCompra") Long id) {
        LOG.infof("Buscando historíco entrega de uma compra: %s", id);
        Result result = null;

        try {
            List<HistoricoEntregaResponseDTO> response = service.getHistoricoEntrega(id);
            LOG.infof("Históricos buscados com sucesso.");
            return Response.ok().entity(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao buscar histórico.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }

        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

    @PATCH
    @RolesAllowed({"Admin", "User"})
    @Path("/{idCompra}/historico-entrega")
    public Response insertHistoricoEntrega(@PathParam("idCompra") Long id, @Valid HistoricoEntregaDTO dto) {
        LOG.infof("Inserindo historico de entrega: %s", id);
        Result result = null;

        try {
            HistoricoEntregaResponseDTO response = service.insertHistoricoEntrega(id, dto);
            LOG.infof("Histórico inserido com sucesso.", response.id());
            return Response.ok().entity(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir um historico.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }

        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

    @PATCH
    @RolesAllowed({"Admin", "User"})
    @Path("/{idCompra}/pagamento/boleto")
    public Response pagarBoleto(@PathParam("idCompra") Long id, @Valid BoletoDTO dto) {
        LOG.info("Realizando pagamento por boleto");
        Result result = null;

        try {
            BoletoResponseDTO response = service.pagarPorBoleto(id, dto);
            LOG.infof("Pagamento realizado com sucesso.");
            return Response.ok().entity(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao fazer pagamento por boleto.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }

        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

    @PATCH
    @RolesAllowed({"Admin", "User"})
    @Path("/{idCompra}/pagamento/pix")
    public Response pagarPix(@PathParam("idCompra") Long id, @Valid PixDTO dto) {
        LOG.info("Realizando pagamento por PIX");
        Result result = null;

        try {
            PixResponseDTO response = service.pagarPorPix(id, dto);
            LOG.infof("Pagamento realizado com sucesso.");
            return Response.ok().entity(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao fazer pagamento por pix.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }

        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

    @GET
    @RolesAllowed({"Admin", "User"})
    @Path("/{idCompra}/pagamento")
    public Response getMetodoDePagamento(@PathParam("idCompra") Long id) {
        LOG.info("Consultando método de pagamento");
        Result result = null;

        try {
            Response response = service.getMetodoPagamento(id);
            LOG.infof("Busca de método de pagamento realizada com sucesso.");
            return response;
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao fazer consulta.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }

        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

}