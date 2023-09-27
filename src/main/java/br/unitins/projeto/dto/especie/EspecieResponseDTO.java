package br.unitins.projeto.dto.especie;

import br.unitins.projeto.model.Especie;

public record EspecieResponseDTO(
        Long id,
        String nome,
        Boolean ativo) {

    public EspecieResponseDTO(Especie entity) {
        this(entity.getId(), entity.getNome(), entity.getAtivo());
    }

}
