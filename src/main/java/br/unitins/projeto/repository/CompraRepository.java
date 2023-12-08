package br.unitins.projeto.repository;

import java.util.List;

import br.unitins.projeto.model.Compra;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CompraRepository implements PanacheRepository<Compra> {

    public PanacheQuery<Compra> findByUsuario(Long id) {
        if (id == null)
            return null;

        return find("usuario.id = ?1 ", id);
    }

}
