package br.unitins.projeto.dto.metodo.pagamento.pix;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PixDTO(

        @NotBlank(message = "A chave pix deve ser informada.")
        String chave,

        @NotNull(message = "O tipo da chave deve ser informado.")
        Integer tipoChavePix

) {
}