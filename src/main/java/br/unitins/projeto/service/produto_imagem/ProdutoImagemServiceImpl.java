package br.unitins.projeto.service.produto_imagem;

import br.unitins.projeto.model.ProdutoImagem;
import br.unitins.projeto.repository.ProdutoImagemRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class ProdutoImagemServiceImpl implements ProdutoImagemService {

    @Inject
    ProdutoImagemRepository repository;

    @Override
    public ProdutoImagem findById(Long id) {
        ProdutoImagem produtoImagem = repository.findById(id);

        if (produtoImagem == null)
            throw new NotFoundException("Imagem não encontrada.");

        return produtoImagem;
    }

    @Override
    @Transactional
    public ProdutoImagem create(ProdutoImagem produtoImagem) throws ConstraintViolationException {
        repository.persist(produtoImagem);
        return produtoImagem;
    }

    @Override
    @Transactional
    public ProdutoImagem update(Long id, ProdutoImagem produtoImagem) throws ConstraintViolationException {
        ProdutoImagem imagem = repository.findById(id);
        imagem = produtoImagem;

        return imagem;
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

}