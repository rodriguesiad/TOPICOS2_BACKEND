package br.unitins.projeto.repository;

import br.unitins.projeto.model.HistoricoEntrega;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class HistoricoEntregaRepository implements PanacheRepository<HistoricoEntrega> {

    public List<HistoricoEntrega> findByCompra(Long id) {
        if (id == null)
            return null;

        return find("compra.id = ?1 ", id).list();
    }

}
