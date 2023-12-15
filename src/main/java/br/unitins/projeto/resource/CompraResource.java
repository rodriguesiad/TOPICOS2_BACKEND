package br.unitins.projeto.resource;

import br.unitins.projeto.dto.compra.CompraDTO;
import br.unitins.projeto.dto.compra.CompraResponseDTO;
import br.unitins.projeto.dto.compra.StatusCompraDTO;
import br.unitins.projeto.dto.historico_entrega.HistoricoEntregaDTO;
import br.unitins.projeto.dto.historico_entrega.HistoricoEntregaResponseDTO;
import br.unitins.projeto.dto.metodo.pagamento.boleto.BoletoResponseDTO;
import br.unitins.projeto.dto.metodo.pagamento.pix.PixResponseDTO;
import br.unitins.projeto.dto.usuario.UsuarioResponseDTO;
import br.unitins.projeto.model.StatusCompra;
import br.unitins.projeto.service.compra.CompraService;
import br.unitins.projeto.service.usuario.UsuarioService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
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

    @GET
    @Path("/all")
    public List<CompraResponseDTO> getByUsuarioPaginado(
            @QueryParam("page") int pageNumber,
            @QueryParam("pageSize") int pageSize
    ) {
        return service.findAllByUsuario(getIdUsuario(), pageNumber, pageSize);
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"Administrador", "Comum"})
    public CompraResponseDTO findById(@PathParam("id") Long id) {
        LOG.info("Buscando uma compra pelo id.");
        return service.findById(id);
    }

    @POST
    @RolesAllowed({"Administrador", "Comum"})
    public Response insert(@Valid CompraDTO dto) {
        LOG.info("Inserindo uma compra");
        CompraResponseDTO response = service.create(dto, getIdUsuario());
        LOG.infof("Compra criada com sucesso.", response.id());
        return Response.status(Status.CREATED).entity(response).build();
    }

    @PATCH
    @RolesAllowed({"Administrador", "Comum"})
    @Path("/{idCompra}/alterar-status")
    public Response alterStatus(@PathParam("idCompra") Long id, @Valid StatusCompraDTO dto) {
        LOG.info("Alterando status de uma compra");
        CompraResponseDTO response = service.alterStatusCompra(id, dto);
        LOG.infof("Status alterado com sucesso.", response.id());
        return Response.ok().entity(response).build();
    }

    @GET
    @RolesAllowed({"Administrador", "Comum"})
    @Path("/{idCompra}/historico-entrega")
    public Response getHistoricoEntrega(@PathParam("idCompra") Long id) {
        LOG.infof("Buscando historíco entrega de uma compra: %s", id);
        List<HistoricoEntregaResponseDTO> response = service.getHistoricoEntrega(id);
        LOG.infof("Históricos buscados com sucesso.");
        return Response.ok().entity(response).build();
    }

    @PATCH
    @RolesAllowed({"Administrador", "Comum"})
    @Path("/{idCompra}/historico-entrega")
    public Response insertHistoricoEntrega(@PathParam("idCompra") Long id, @Valid HistoricoEntregaDTO dto) {
        LOG.infof("Inserindo historico de entrega: %s", id);
        HistoricoEntregaResponseDTO response = service.insertHistoricoEntrega(id, dto);
        LOG.infof("Histórico inserido com sucesso.", response.id());
        return Response.ok().entity(response).build();
    }

    @PATCH
    @RolesAllowed({"Administrador", "Comum"})
    @Path("/{idCompra}/pagamento/boleto")
    public Response pagarBoleto(@PathParam("idCompra") Long id) {
        LOG.info("Realizando pagamento por boleto");
        BoletoResponseDTO response = service.pagarPorBoleto(id);
        LOG.infof("Pagamento realizado com sucesso.");
        return Response.ok().entity(response).build();
    }

    @PATCH
    @RolesAllowed({"Administrador", "Comum"})
    @Path("/{idCompra}/pagamento/pix")
    public Response pagarPix(@PathParam("idCompra") Long id) {
        LOG.info("Realizando pagamento por pix");
        PixResponseDTO response = service.pagarPorPix(id);
        LOG.infof("Pagamento realizado com sucesso.");
        return Response.ok().entity(response).build();
    }


    @GET
    @RolesAllowed({"Administrador", "Comum"})
    @Path("/{idCompra}/pagamento")
    public Response getMetodoDePagamento(@PathParam("idCompra") Long id) {
        LOG.info("Consultando método de pagamento");
        BoletoResponseDTO response = service.getBoleto(id);
        return Response.ok().entity(response).build();
    }

    @GET
//    @RolesAllowed({"Admin", "User"})
    @Path("/{idCompra}/pagamento/pix")
    public Response getMetodoDePagamentoPix(@PathParam("idCompra") Long id) {
        LOG.info("Consultando método de pagamento");
        PixResponseDTO response = service.getPix(id);
        return Response.ok().entity(response).build();
    }

    @GET
    @Path("/status-compra")
    public Response getStatusCompra() {
        return Response.ok(StatusCompra.values()).build();
    }

}