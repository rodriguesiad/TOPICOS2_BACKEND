package br.unitins.projeto.dto.metodo.pagamento.boleto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record BoletoDTO(

        @NotBlank(message = "O número do boleto deve ser informado.")
        String numeroBoleto,

        @NotNull()
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate vencimento

) {
}