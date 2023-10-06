package br.unitins.projeto.repository;

import br.unitins.projeto.model.Especie;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class EspecieRepository implements PanacheRepository<Especie> {

    public List<Especie> findByNome(String nome) {
        if (nome == null)
            return null;
        return find("UPPER(nome) LIKE ?1 ", "%" + nome.toUpperCase() + "%").list();
    }
        public PanacheQuery<Especie> findByFiltro(String nome, Boolean ativo) {
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
