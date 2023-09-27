package br.unitins.projeto.resource;

import br.unitins.projeto.dto.auth_usuario.AuthUsuarioDTO;
import br.unitins.projeto.model.Usuario;
import br.unitins.projeto.service.hash.HashService;
import br.unitins.projeto.service.token_jwt.TokenJwtService;
import br.unitins.projeto.service.usuario.UsuarioService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    HashService hashService;

    @Inject
    UsuarioService usuarioService;

    @Inject
    TokenJwtService tokenService;

    @Inject
    JsonWebToken jwt;

    private static final Logger LOG = Logger.getLogger(AuthResource.class);

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(@Valid AuthUsuarioDTO authDTO) {
        LOG.infof("Fazendo login de usuário: %s", authDTO.login());
        String hash = hashService.getHashSenha(authDTO.senha());

        Usuario usuario = usuarioService.findByLoginAndSenha(authDTO.login(), hash);

        if (usuario == null) {
            return Response.status(Status.NO_CONTENT)
                    .entity("Usuario não encontrado").build();
        }
        return Response.ok()
                .header("Authorization", tokenService.generateJwt(usuario))
                .build();
    }

}
