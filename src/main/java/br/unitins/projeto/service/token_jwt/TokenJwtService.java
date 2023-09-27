package br.unitins.projeto.service.token_jwt;

import br.unitins.projeto.model.Usuario;

public interface TokenJwtService {
    public String generateJwt(Usuario usuario);
}
