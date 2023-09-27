package br.unitins.projeto.dto.raca;

import br.unitins.projeto.model.Raca;

public record RacaResponseDTO(
        Long id,
        String nome,
        Boolean ativo) {

    public RacaResponseDTO(Raca entity) {
        this(entity.getId(), entity.getNome(), entity.getAtivo());
    }

}
