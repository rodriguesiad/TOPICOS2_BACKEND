package br.unitins.projeto.dto.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EnderecoUpdateDTO(

                Long id,

                @NotNull(message = "O campo principal deve ser informado") Boolean principal,

                @NotBlank(message = "O logradouro deve ser informado") @Size(max = 100, message = "O logradouro só pode ter até 100 caracteres.") String logradouro,

                @NotBlank(message = "O bairro deve ser informado") @Size(max = 100, message = "O bairro só pode ter até 100 caracteres.") String bairro,

                @NotBlank(message = "O número deve ser informado") @Size(max = 100, message = "O número só pode ter até 100 caracteres.") String numero,

                @Size(max = 150, message = "O complemento só pode ter até 150 caracteres.") String complemento,

                @NotBlank(message = "O CEP deve ser informado") @Size(max = 8, message = "O CEP só pode ter até 8 caracteres.") String cep,

                @Size(max = 60, message = "O título só pode ter até 60 caracteres.") String titulo,

                @NotNull(message = "O município deve ser informado.") Long idMunicipio

) {

}
