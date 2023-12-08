package br.unitins.projeto.dto.historico_entrega;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record HistoricoEntregaDTO(

        @NotBlank(message = "O título deve ser informado.")
        @Size(max = 40, message = "O número máximo de caracteres é 40.")
        String titulo,

        @Size(max = 100, message = "O número máximo de caracteres é 100.")
        String descricao

) {
}
