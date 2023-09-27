package br.unitins.projeto.dto.usuario.cadastro;

import br.unitins.projeto.dto.telefone.TelefoneDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.List;

public record CadastroAdminDTO(

        @NotBlank(message = "O campo nome deve ser informado.") @Size(max = 60, message = "O campo nome deve possuir no máximo 60 caracteres.")
        String nome,

        @Email @NotBlank(message = "O campo e-mail deve ser informado.") String email,

        @CPF @NotBlank(message = "O campo CPF deve ser informado.") @Size(max = 11, min = 11, message = "O CPF deve ter 11 caracteres") String cpf,

        @NotBlank(message = "O campo senha deve ser informado.") String senha,

        @NotNull(message = "A data de nascimento deve ser informada.") @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate dataNascimento,

        @NotNull(message = "O perfil deve ser informado.")
        Integer perfil,
        
        List<TelefoneDTO> telefones
) {
}
