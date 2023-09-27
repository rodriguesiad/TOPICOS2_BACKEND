package br.unitins.projeto.dto.historico_entrega;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record HistoricoEntregaDTO(

        @NotBlank(message = "O título deve ser informado.")
        @Size(max = 40, message = "O número máximo de caracteres é 40.")
        String titulo,

        @Size(max = 100, message = "O número máximo de caracteres é 100.")
        String descricao

) {
}
