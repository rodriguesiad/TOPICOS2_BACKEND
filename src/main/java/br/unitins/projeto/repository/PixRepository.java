package br.unitins.projeto.repository;

import br.unitins.projeto.model.Pix;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PixRepository implements PanacheRepository<Pix> {

    public Pix findByIdAndCompra(Long id, Long idCompra) {
        if (id == null || idCompra == null)
            return null;
        return find("id = ?1 AND compra.id = ?2", id, idCompra).firstResult();
    }

}
