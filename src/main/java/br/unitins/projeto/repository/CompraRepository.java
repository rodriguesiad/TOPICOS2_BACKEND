package br.unitins.projeto.repository;

import br.unitins.projeto.model.Compra;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class CompraRepository implements PanacheRepository<Compra> {

    public List<Compra> findByUsuario(Long id) {
        if (id == null)
            return null;

        return find("usuario.id = ?1 ", id).list();
    }

}
