package br.unitins.projeto.dto.usuario;

import br.unitins.projeto.dto.endereco.EnderecoResponseDTO;
import br.unitins.projeto.dto.telefone.TelefoneResponseDTO;
import br.unitins.projeto.model.Endereco;
import br.unitins.projeto.model.Telefone;
import br.unitins.projeto.model.Usuario;

import java.util.List;
import java.util.stream.Collectors;

public record UsuarioResponseDTO(

        Long id,

        String nome,

        String email,

        String cpf,

        String login,

        String nomeImagem,

        List<TelefoneResponseDTO> telefones,

        List<EnderecoResponseDTO> enderecos

) {

    public static UsuarioResponseDTO valueOf(Usuario entity) {
        if (entity.getPessoaFisica() == null)
            return new UsuarioResponseDTO(entity.getId(),
                    null,
                    null,
                    null,
                    entity.getLogin(),
                    entity.getNomeImagem(),
                    gerarTelefoneDTO(entity.getListaTelefone()),
                    gerarEnderecoDTO(entity.getListaEndereco()));

        return new UsuarioResponseDTO(entity.getId(),
                entity.getPessoaFisica().getCpf(),
                entity.getPessoaFisica().getNome(),
                entity.getPessoaFisica().getEmail(),
                entity.getLogin(),
                entity.getNomeImagem(),
                gerarTelefoneDTO(entity.getListaTelefone()),
                gerarEnderecoDTO(entity.getListaEndereco()));
    }

    public static List<EnderecoResponseDTO> gerarEnderecoDTO(List<Endereco> list) {
        if (list != null)
            return list.stream().map(EnderecoResponseDTO::new).collect(Collectors.toList());
        return null;
    }

//    public static List<ArtigoCeramicaResponseDTO> gerarListaDesejoDTO(List<Produto> list) {
//        if (list != null)
//            return list.stream()
//                    .map(produto -> (ArtigoCeramica) produto)
//                    .map(ArtigoCeramicaResponseDTO::new)
//                    .collect(Collectors.toList());
//        return null;
//    }

    public static List<TelefoneResponseDTO> gerarTelefoneDTO(List<Telefone> list) {
        if (list != null) {
            return list.stream()
                    .map(TelefoneResponseDTO::new)
                    .collect(Collectors.toList());
        }
        return null;
    }

}