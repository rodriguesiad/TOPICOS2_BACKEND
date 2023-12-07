package br.unitins.projeto.resource;

import br.unitins.projeto.application.Result;
import br.unitins.projeto.dto.compra.CompraResponseDTO;
import br.unitins.projeto.dto.endereco.EnderecoDTO;
import br.unitins.projeto.dto.endereco.EnderecoResponseDTO;
import br.unitins.projeto.dto.endereco.EnderecoUpdateDTO;
import br.unitins.projeto.dto.usuario.UsuarioResponseDTO;
import br.unitins.projeto.dto.usuario.dados_pessoais.DadosPessoaisDTO;
import br.unitins.projeto.dto.usuario.dados_pessoais.DadosPessoaisResponseDTO;
import br.unitins.projeto.dto.usuario.enderecos.UsuarioEnderecoResponseDTO;
import br.unitins.projeto.dto.usuario.senha.SenhaDTO;
import br.unitins.projeto.dto.usuario.telefone.UsuarioTelefoneDTO;
import br.unitins.projeto.dto.usuario.telefone.UsuarioTelefoneResponseDTO;
import br.unitins.projeto.form.ProdutoImageForm;
import br.unitins.projeto.service.compra.CompraService;
import br.unitins.projeto.service.file.FileService;
import br.unitins.projeto.service.usuario.UsuarioService;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import java.io.IOException;
import java.util.List;

@Path("/usuario-logado")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioLogadoResource {

    @Inject
    UsuarioService service;

    @Inject
    CompraService compraService;

    @Inject
    JsonWebToken jwt;

    @Inject
    FileService fileService;

    private static final Logger LOG = Logger.getLogger(UsuarioLogadoResource.class);


    private Long getIdUsuario() {
        String login = jwt.getSubject();
        UsuarioResponseDTO usuario = service.findByLogin(login);
        return usuario.id();
    }

    @GET
//    @RolesAllowed({"Admin", "User"})
    public Response getPerfilUsuario() {
//        String login = jwt.getSubject();
        String login = "maria";
        UsuarioResponseDTO usuario = service.findByLogin(login);
        LOG.info("Buscando perfis do usuário logado.");
        return Response.ok(usuario).build();
    }

    @GET
    @Path("/dados-pessoais")
//    @RolesAllowed({"Admin", "User"})
    public Response getDadosPessoais() {
        LOG.info("Buscando dados pessoais do usuario");
        Result result = null;

        try {
            DadosPessoaisResponseDTO response = service.getDadosPessoais(this.getIdUsuario());
            LOG.infof("Pequisa realizada com sucesso.", response.id());
            return Response.ok(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao pesquisar dados pessoais.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }

        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

    @PATCH
    @Path("/dados-pessoais")
//    @RolesAllowed({"Admin", "User"})
    public Response setDadosPessoais(@Valid DadosPessoaisDTO dto) {
        LOG.infof("Alterando dados pessoais");
        Result result = null;

        try {
            DadosPessoaisResponseDTO response = service.updateDadosPessoais(this.getIdUsuario(), dto);
            LOG.infof("Dados pessoais alterados com sucesso.", response.id());
            return Response.ok(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao alterar os dados pessoais.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }

        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

    @PATCH
    @Path("/alterar-senha")
//    @RolesAllowed({"Admin", "User"})
    public Response alterarSenha(@Valid SenhaDTO dto) {
        LOG.info("Atualizando senha");
        Result result = null;

        try {
            Boolean response = service.updateSenha(this.getIdUsuario(), dto);
            LOG.info("Senha alterada com sucesso.");
            return Response.ok(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao alterar senha.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }

        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

    @GET
    @Path("/enderecos")
//    @RolesAllowed({"Admin", "User"})
    public Response getEnderecos() {
        LOG.info("Buscando endereços");
        Result result = null;

        try {
            UsuarioEnderecoResponseDTO response = service.getEnderecos(this.getIdUsuario());
            LOG.infof("Pesquisa realizada com sucesso.");
            return Response.ok(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao pesquisar endereços.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }

        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

    @PATCH
    @Path("/enderecos/{idEndereco}")
//    @RolesAllowed({"Admin", "User"})
    public Response updateEnderecos(@PathParam("idEndereco") Long idEndereco, @Valid EnderecoUpdateDTO dto) {
        LOG.info("Alterando endereços");
        Result result = null;

        try {
            UsuarioEnderecoResponseDTO response = service.updateEndereco(this.getIdUsuario(), idEndereco, dto);
            LOG.infof("Endereço alterado com sucesso.");
            return Response.ok(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao alterar endereço.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }

        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

    @DELETE
    @Path("/enderecos/{idEndereco}")
//    @RolesAllowed({"Admin", "User"})
    public Response deleteEndereco(@PathParam("idEndereco") Long idEndereco) {
        LOG.infof("Deletando um endereço");
        Result result = null;

        try {
            service.deleteEndereco(this.getIdUsuario(), idEndereco);
            LOG.infof("Endereço (%d) deletado com sucesso.", idEndereco);
            return Response.status(Status.NO_CONTENT).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao deletar endereço.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }

        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

    @PATCH
    @Path("/enderecos")
//    @RolesAllowed({"Admin", "User"})
    public Response insertEnderecos(@Valid EnderecoDTO dto) {
        LOG.infof("Inserindo um endereço");
        Result result = null;

        try {
            EnderecoResponseDTO response = service.insertEndereco(this.getIdUsuario(), dto);
            LOG.infof("Endereço (%d) inserido com sucesso.", response.id());
            return Response.ok(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao inserir endereço.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }

        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

    @GET
    @Path("/contatos")
//    @RolesAllowed({"Admin", "User"})
    public Response getContatos() {
        LOG.info("Buscando contatos");
        Result result = null;

        try {
            UsuarioTelefoneResponseDTO response = service.getTelefone(this.getIdUsuario());
            LOG.infof("Pesquisa realizada com sucesso.");
            return Response.ok(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao buscar contatos.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }

        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

    @PATCH
    @Path("/contatos")
//    @RolesAllowed({"Admin", "User"})
    public Response alterarContatos(@Valid UsuarioTelefoneDTO dto) {
        LOG.info("Alterando contatos");
        Result result = null;

        try {
            UsuarioTelefoneResponseDTO response = service.updateTelefone(this.getIdUsuario(), dto);
            LOG.info("Contatos alterados com sucesso.");
            return Response.ok(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao alterar contatos.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }

        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

    @PATCH
    @Path("/imagem")
//    @RolesAllowed({"Admin", "User"})
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response salvarImagem(@MultipartForm ProdutoImageForm form) {
        String nomeImagem = "";
        Long id = this.getIdUsuario();

        try {
            nomeImagem = fileService.salvarImagem(form.getImagem(), form.getNomeImagem(), "usuario");
        } catch (IOException e) {
            Result result = new Result(e.getMessage());
            return Response.status(Status.CONFLICT).entity(result).build();
        }

        UsuarioResponseDTO usuario = service.update(id, nomeImagem);
        return Response.ok(usuario).build();
    }

    @GET
    @Path("/download/{nomeImagem}")
//    @RolesAllowed({"Admin", "User"})
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response download(@PathParam("nomeImagem") String nomeImagem) {
        Response.ResponseBuilder response = Response.ok(fileService.download(nomeImagem, "usuario"));
        response.header("Content-Disposition", "attachment;filename=" + nomeImagem);

        return response.build();
    }

    @GET
    @Path("/minhas-compras")
//    @RolesAllowed({"Admin", "User"})
    public Response minhasCompras() {
        LOG.infof("Buscando compras");
        Result result = null;

        try {
            List<CompraResponseDTO> response = compraService.findByUsuario(this.getIdUsuario());
            LOG.info("Pesquisa realizada com sucesso.");
            return Response.ok(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao buscar compras.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }

        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

//    @GET
//    @Path("/lista-desejo")
//    @RolesAllowed({"Admin", "User"})
//    public Response getListaDesejo() {
//        LOG.info("Buscando lista de desejo");
//        Result result = null;
//
//        try {
//            UsuarioListaDesejoResponseDTO response = service.getListaDesejo(this.getIdUsuario());
//            LOG.infof("Pesquisa realizada com sucesso.");
//            return Response.ok(response).build();
//        } catch (ConstraintViolationException e) {
//            LOG.error("Erro ao pesquisar lista desejo.");
//            LOG.debug(e.getMessage());
//            result = new Result(e.getConstraintViolations());
//        } catch (Exception e) {
//            LOG.fatal("Erro sem identificacao: " + e.getMessage());
//            result = new Result(e.getMessage(), false);
//        }
//
//        return Response.status(Status.NOT_FOUND).entity(result).build();
//    }

//    @DELETE
//    @Path("/lista-desejo/{idProduto}")
//    @RolesAllowed({"Admin", "User"})
//    public Response deleteProdutoListaDesejo(@PathParam("idProduto") Long idProduto) {
//        LOG.infof("Deletando um produto da lista de desejo");
//        Result result = null;
//
//        try {
//            service.deleteItemListaDesejo(this.getIdUsuario(), idProduto);
//            LOG.infof("Produto (%d) deletado da lista de desejo com sucesso.", idProduto);
//            return Response.status(Status.NO_CONTENT).build();
//        } catch (ConstraintViolationException e) {
//            LOG.error("Erro ao deletar endereço.");
//            LOG.debug(e.getMessage());
//            result = new Result(e.getConstraintViolations());
//        } catch (Exception e) {
//            LOG.fatal("Erro sem identificacao: " + e.getMessage());
//            result = new Result(e.getMessage(), false);
//        }
//
//        return Response.status(Status.NOT_FOUND).entity(result).build();
//    }

//    @PATCH
//    @Path("/lista-desejo")
//    @RolesAllowed({"Admin", "User"})
//    public Response insertListaDesejo(@Valid ListaDesejoDTO dto) {
//        LOG.infof("Inserindo um produto na lista de desejo");
//        Result result = null;
//
//        try {
//            UsuarioListaDesejoResponseDTO response = service.insertProdutoListaDesejo(this.getIdUsuario(), dto);
//            LOG.infof("Produto (%d) inserido com sucesso.");
//            return Response.ok(response).build();
//        } catch (ConstraintViolationException e) {
//            LOG.error("Erro ao inserir produto.");
//            LOG.debug(e.getMessage());
//            result = new Result(e.getConstraintViolations());
//        } catch (Exception e) {
//            LOG.fatal("Erro sem identificacao: " + e.getMessage());
//            result = new Result(e.getMessage(), false);
//        }
//
//        return Response.status(Status.NOT_FOUND).entity(result).build();
//    }

}
