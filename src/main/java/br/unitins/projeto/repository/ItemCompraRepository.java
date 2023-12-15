package br.unitins.projeto.repository;

import java.util.List;

import br.unitins.projeto.model.Compra;
import br.unitins.projeto.model.ItemCompra;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ItemCompraRepository implements PanacheRepository<ItemCompra> {

    public List<ItemCompra> findByProduto(Long id) {
        if (id == null)
            return null;

        return find("produto.id = ?1 ", id).list();
    }

}
