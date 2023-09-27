package br.unitins.projeto.dto.produto_imagem;

import br.unitins.projeto.model.ProdutoImagem;

public record ProdutoImagemResponseDTO(
        String nomeArquivo
) {

    public ProdutoImagemResponseDTO(ProdutoImagem entity) {
        this(entity.getNomeArquivo());
    }

}