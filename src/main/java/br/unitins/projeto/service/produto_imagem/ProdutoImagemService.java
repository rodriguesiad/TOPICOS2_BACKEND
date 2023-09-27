package br.unitins.projeto.service.produto_imagem;

import br.unitins.projeto.model.ProdutoImagem;

public interface ProdutoImagemService {

    ProdutoImagem findById(Long id);

    ProdutoImagem create(ProdutoImagem produtoImagem);

    ProdutoImagem update(Long id, ProdutoImagem produtoImagem);

    void delete(Long id);

}