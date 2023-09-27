package br.unitins.projeto.repository;

import br.unitins.projeto.model.ItemCompra;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ItemCompraRepository implements PanacheRepository<ItemCompra> {

}
