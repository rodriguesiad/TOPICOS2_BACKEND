package br.unitins.projeto.dto.produto;

import br.unitins.projeto.model.Produto;

public record ProdutoResponseDTO(
        String nome,
        Double preco
) {

    public ProdutoResponseDTO(Produto entity) {
        this(entity.getNome(), entity.getPreco());
    }
}
