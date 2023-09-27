package br.unitins.projeto.dto.metodo.pagamento.pix;

import br.unitins.projeto.model.Pix;
import br.unitins.projeto.model.TipoChavePix;

public record PixResponseDTO(

        Long id,
        String chave,
        TipoChavePix tipoChavePix

) {

    public PixResponseDTO(Pix entity) {
        this( entity.getId(), entity.getChave(), entity.getTipoChavePix());
    }

}