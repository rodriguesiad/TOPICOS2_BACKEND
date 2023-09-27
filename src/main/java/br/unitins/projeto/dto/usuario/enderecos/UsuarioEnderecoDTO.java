package br.unitins.projeto.dto.usuario.enderecos;

import java.util.List;

import br.unitins.projeto.dto.endereco.EnderecoDTO;
import jakarta.validation.Valid;

public record UsuarioEnderecoDTO(

        @Valid List<EnderecoDTO> listaEnderecos

) {

}
