package br.unitins.projeto.dto.usuario.cadastro;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import br.unitins.projeto.dto.telefone.TelefoneResponseDTO;
import br.unitins.projeto.model.Perfil;
import br.unitins.projeto.model.Telefone;
import br.unitins.projeto.model.Usuario;

public record CadastroAdminResponseDTO(

        Long id,
        String nome,
        String email,
        String cpf,
        String senha,
        LocalDate dataNascimento,
        List<Perfil> perfis,
        List<TelefoneResponseDTO> telefones,
        Boolean ativo

) {

    public static CadastroAdminResponseDTO valueOf(Usuario entity) {
        if (entity.getPessoaFisica() == null)
            return new CadastroAdminResponseDTO(
                    entity.getId(),
                    null,
                    null,
                    null,
                    entity.getSenha(),
                    null,
                    entity.getPerfis(),
                    gerarTelefoneDTO(entity.getListaTelefone()),
                    entity.getAtivo());

        return new CadastroAdminResponseDTO(
                entity.getId(),
                entity.getPessoaFisica().getNome(),
                entity.getPessoaFisica().getEmail(),
                entity.getPessoaFisica().getCpf(),
                entity.getSenha(),
                entity.getPessoaFisica().getDataNascimento(),
                entity.getPerfis(),
                gerarTelefoneDTO(entity.getListaTelefone()),
                entity.getAtivo());
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
