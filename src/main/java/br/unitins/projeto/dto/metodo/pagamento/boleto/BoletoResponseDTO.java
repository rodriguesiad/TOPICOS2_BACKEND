package br.unitins.projeto.dto.metodo.pagamento.boleto;

import br.unitins.projeto.model.Boleto;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record BoletoResponseDTO(

        Long id,
        String numeroBoleto,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate dataVencimento,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy 00:00:00")
        LocalDateTime dataPagamento,

        String banco,

        String agencia,

        String conta,

        String nome,

        String cnpj

) {
    public BoletoResponseDTO(Boleto entity) {
        this(entity.getId(), entity.getNumeroBoleto(), entity.getVencimento(),
                entity.getDataPagamento(), entity.getBanco(), entity.getAgencia(),
                entity.getConta(), entity.getNome(), entity.getCnpj());
    }

}