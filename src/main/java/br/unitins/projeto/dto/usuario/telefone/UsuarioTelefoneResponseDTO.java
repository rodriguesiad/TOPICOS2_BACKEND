package br.unitins.projeto.dto.usuario.telefone;

import br.unitins.projeto.dto.telefone.TelefoneResponseDTO;
import br.unitins.projeto.model.Telefone;
import br.unitins.projeto.model.Usuario;

import java.util.List;
import java.util.stream.Collectors;

public record UsuarioTelefoneResponseDTO(
        List<TelefoneResponseDTO> telefones
) {

    public static UsuarioTelefoneResponseDTO valueOf(Usuario entity) {
        return new UsuarioTelefoneResponseDTO(gerarTelefoneDTO(entity.getListaTelefone()));
    }

    public static List<TelefoneResponseDTO> gerarTelefoneDTO(List<Telefone> list) {
        if (list != null) {
            return list.stream()
                    .map(TelefoneResponseDTO::new)
                    .collect(Collectors.toList());
        }
        return null;
    }
}
