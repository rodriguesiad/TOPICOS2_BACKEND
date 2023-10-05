package br.unitins.projeto.dto.metodo.recebimento.boleto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;

public record BoletoRecebimentoDTO(
        @NotBlank(message = "O banco deve ser informado.") String banco,

        @NotBlank(message = "O nome deve ser informado.") String nome,

        @NotBlank(message = "O CNPJ deve ser informado.") @CNPJ String cnpj,

        @NotBlank(message = "A agência deve ser informada.") String agencia,

        @NotBlank(message = "A conta deve ser informada.") String conta
) {
}