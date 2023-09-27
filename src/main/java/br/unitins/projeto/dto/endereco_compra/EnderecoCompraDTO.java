package br.unitins.projeto.dto.endereco_compra;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EnderecoCompraDTO(

        @NotBlank(message = "O logradouro deve ser informado") @Size(max = 100, message = "O logradouro só pode ter até 100 caracteres.") String logradouro,

        @NotBlank(message = "O bairro deve ser informado") @Size(max = 100, message = "O bairro só pode ter até 100 caracteres.") String bairro,

        @NotBlank(message = "O número deve ser informado") @Size(max = 100, message = "O número só pode ter até 100 caracteres.") String numero,

        @Size(max = 150, message = "O complemento só pode ter até 150 caracteres.") String complemento,

        @NotBlank(message = "O CEP deve ser informado") @Size(max = 8, message = "O CEP só pode ter até 8 caracteres.") String cep,

        @NotNull(message = "O município deve ser informado.") Long idMunicipio

) {

}
