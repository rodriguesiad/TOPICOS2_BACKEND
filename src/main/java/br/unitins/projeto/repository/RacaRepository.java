package br.unitins.projeto.repository;

import br.unitins.projeto.model.Categoria;
import br.unitins.projeto.model.Raca;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class RacaRepository implements PanacheRepository<Raca> {

    public PanacheQuery<Raca> findByFiltro(String nome, Boolean ativo) {
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

}
