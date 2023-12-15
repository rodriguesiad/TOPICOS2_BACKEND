package br.unitins.projeto.dto.usuario.dados_pessoais;

import br.unitins.projeto.dto.telefone.TelefoneDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.List;

public record DadosPessoaisDTO(

        @NotBlank(message = "O campo nome deve ser informado.") @Size(max = 60, message = "O campo nome deve possuir no máximo 60 caracteres.")
        String nome,

        @CPF(message = "CPF inválido") @NotBlank(message = "O campo CPF deve ser informado.") @Size(max = 11, min = 11, message = "O CPF deve ter 11 caracteres.") String cpf,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate dataNascimento,

        @Valid
        List<TelefoneDTO> telefones) {

}
