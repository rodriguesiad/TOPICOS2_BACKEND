package br.unitins.projeto.dto.historico_entrega;

import br.unitins.projeto.model.HistoricoEntrega;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

public record HistoricoEntregaResponseDTO(

        Long id,

        String titulo,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        String descricao,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDateTime data

) {

    public HistoricoEntregaResponseDTO(HistoricoEntrega entity) {
        this(entity.getId(), entity.getTitulo(), entity.getDescricao(), entity.getData());
    }

}
