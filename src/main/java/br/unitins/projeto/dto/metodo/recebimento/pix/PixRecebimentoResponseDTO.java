package br.unitins.projeto.dto.metodo.recebimento.pix;

import br.unitins.projeto.model.PixRecebimento;
import br.unitins.projeto.model.TipoChavePix;

public record PixRecebimentoResponseDTO(

        Long id,
        String chave,
        TipoChavePix tipoChavePix,
        Boolean ativo

) {

    public PixRecebimentoResponseDTO(PixRecebimento entity) {
        this( entity.getId(), entity.getChave(), entity.getTipoChavePix(), entity.getAtivo());
    }

}