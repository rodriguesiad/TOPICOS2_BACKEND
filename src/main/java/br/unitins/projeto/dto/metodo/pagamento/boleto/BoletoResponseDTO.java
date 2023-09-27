package br.unitins.projeto.dto.metodo.pagamento.boleto;

import br.unitins.projeto.model.Boleto;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record BoletoResponseDTO(

        Long id,
        String numeroBoleto,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy 00:00:00")
        LocalDate vencimento

) {
    public BoletoResponseDTO(Boleto entity) {
        this(entity.getId(), entity.getNumeroBoleto(), entity.getVencimento());
    }

}