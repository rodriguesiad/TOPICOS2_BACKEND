package br.unitins.projeto.repository;

import java.util.List;

import br.unitins.projeto.model.Produto;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProdutoRepository implements PanacheRepository<Produto> {

    public List<Produto> findByNome(String nome) {
        if (nome == null)
            return null;
        return find("UPPER(nome) LIKE ?1 ", "%" + nome.toUpperCase() + "%").list();
    }

    public PanacheQuery<Produto> findByFiltro(String nome, Boolean ativo) {
        if (nome != null && ativo != null) {
            return find("UPPER(nome) LIKE ?1 AND ativo = ?2 ", "%" + nome.toUpperCase() + "%", ativo);
        }

        if (nome != null && ativo == null) {
            return find("UPPER(nome) LIKE ?1 ", "%" + nome.toUpperCase() + "%");
        }

        if (nome == null && ativo != null) {
            return find("ativo = ?1 ", "%" + nome.toUpperCase() + "%", ativo);
        }

        return null;
    }

    public List<Produto> findProdutosRecomendados(Long idRaca, Long idCategoria, Long idEspecie) {
        List<Produto> produtosRecomendados = find("raca.id = ?1 and categoria.id = ?2 and especie.id = ?3 and ativo = true",
                idRaca, idCategoria, idEspecie).page(0, 4).list();

        if (produtosRecomendados.isEmpty() || produtosRecomendados.size() < 4) {
            produtosRecomendados.addAll(find("categoria.id = ?1 and especie.id = ?2 and ativo = true", idCategoria, idEspecie)
                    .page(0, 4 - produtosRecomendados.size()).list());
        }

        if (produtosRecomendados.isEmpty() || produtosRecomendados.size() < 4) {
            produtosRecomendados.addAll(find("especie.id = ?1 and ativo = true", idEspecie)
                    .page(0, 4 - produtosRecomendados.size()).list());
        }

        if (produtosRecomendados.isEmpty() || produtosRecomendados.size() < 4) {
            produtosRecomendados.addAll(findAll().page(0, 4 - produtosRecomendados.size()).list());
        }

        return produtosRecomendados;
    }
}
