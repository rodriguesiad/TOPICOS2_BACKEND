package br.unitins.projeto.dto.usuario.enderecos;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.projeto.dto.endereco.EnderecoResponseDTO;
import br.unitins.projeto.model.Endereco;
import br.unitins.projeto.model.Usuario;
import jakarta.validation.Valid;

public record UsuarioEnderecoResponseDTO(

        @Valid List<EnderecoResponseDTO> listaEnderecos

) {

    public static UsuarioEnderecoResponseDTO valueOf(Usuario entity) {
        return new UsuarioEnderecoResponseDTO(gerarEnderecoDTO(entity.getListaEndereco()));
    }

    public static List<EnderecoResponseDTO> gerarEnderecoDTO(List<Endereco> list) {
        if (list != null)
            return list.stream().map(EnderecoResponseDTO::new).collect(Collectors.toList());
        return null;
    }

}