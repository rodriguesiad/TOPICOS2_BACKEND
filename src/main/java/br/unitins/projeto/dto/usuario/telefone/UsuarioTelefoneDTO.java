package br.unitins.projeto.dto.usuario.telefone;

import br.unitins.projeto.dto.telefone.TelefoneDTO;

import java.util.List;

public record UsuarioTelefoneDTO(
        List<TelefoneDTO> telefones
) {

}
