package br.unitins.projeto.repository;

import br.unitins.projeto.model.ProdutoImagem;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProdutoImagemRepository implements PanacheRepository<ProdutoImagem> {

    public ProdutoImagem findByNomeArquivo(String nomeArquivo) {
        if (nomeArquivo == null)
            return null;
        return find("UPPER(nomeArquivo) = ?1 " + nomeArquivo.toUpperCase()).firstResult();
    }

}