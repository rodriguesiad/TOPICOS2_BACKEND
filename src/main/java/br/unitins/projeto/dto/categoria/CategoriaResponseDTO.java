package br.unitins.projeto.dto.categoria;

import br.unitins.projeto.model.Categoria;

public record CategoriaResponseDTO(
        Long id,
        String nome,
        Boolean ativo) {

    public CategoriaResponseDTO(Categoria entity) {
        this(entity.getId(), entity.getNome(), entity.getAtivo());
    }

}
