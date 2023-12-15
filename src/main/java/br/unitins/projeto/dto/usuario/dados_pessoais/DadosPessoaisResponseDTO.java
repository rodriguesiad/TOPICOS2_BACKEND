package br.unitins.projeto.dto.usuario.dados_pessoais;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import br.unitins.projeto.dto.telefone.TelefoneResponseDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import br.unitins.projeto.model.Usuario;

public record DadosPessoaisResponseDTO(
        Long id,
        String nome,
        String cpf,
        String email,
        LocalDate dataNascimento,
        List<TelefoneResponseDTO> telefones
) {

    public static DadosPessoaisResponseDTO valueOf(Usuario entity) {
        if (entity.getPessoaFisica() == null) {
            return new DadosPessoaisResponseDTO(
                    entity.getId(),
                    null, null, null, null, null);
        }

        return new DadosPessoaisResponseDTO(
                entity.getId(),
                entity.getPessoaFisica().getNome(),
                entity.getPessoaFisica().getCpf(),
                entity.getPessoaFisica().getEmail(),
                entity.getPessoaFisica().getDataNascimento(),
                entity.getListaTelefone().stream().map(TelefoneResponseDTO::new).collect(Collectors.toList()));
    }

}
