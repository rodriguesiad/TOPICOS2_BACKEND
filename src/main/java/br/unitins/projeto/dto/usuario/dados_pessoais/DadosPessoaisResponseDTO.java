package br.unitins.projeto.dto.usuario.dados_pessoais;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import br.unitins.projeto.model.Usuario;

public record DadosPessoaisResponseDTO(
        Long id,
        String nome,
        String cpf,
        String email,
        @JsonInclude(JsonInclude.Include.NON_NULL) @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy") LocalDate dataNascimento) {

    public static DadosPessoaisResponseDTO valueOf(Usuario entity) {
        if (entity.getPessoaFisica() == null) {
            return new DadosPessoaisResponseDTO(
                    entity.getId(),
                    null, null, null, null);
        }

        return new DadosPessoaisResponseDTO(
                entity.getId(),
                entity.getPessoaFisica().getNome(),
                entity.getPessoaFisica().getCpf(),
                entity.getPessoaFisica().getEmail(),
                entity.getPessoaFisica().getDataNascimento());
    }

}
