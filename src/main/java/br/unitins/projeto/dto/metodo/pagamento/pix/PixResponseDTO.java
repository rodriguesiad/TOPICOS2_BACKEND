package br.unitins.projeto.dto.metodo.pagamento.pix;

import br.unitins.projeto.model.Pix;
import br.unitins.projeto.model.TipoChavePix;

import java.time.LocalDateTime;

public record PixResponseDTO(

        Long id,
        String chave,
        String tipoChavePix,
        LocalDateTime dataPagamento

) {

    public PixResponseDTO(Pix entity) {
        this( entity.getId(), entity.getChave(), entity.getTipoChavePix().getLabel(), entity.getDataPagamento());
    }

}