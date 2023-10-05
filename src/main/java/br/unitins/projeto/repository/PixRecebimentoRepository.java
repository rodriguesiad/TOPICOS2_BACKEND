package br.unitins.projeto.repository;

import br.unitins.projeto.model.PixRecebimento;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class PixRecebimentoRepository implements PanacheRepository<PixRecebimento> {

    public PixRecebimento findByAtivo() {
        return find("ativo = ?1", true).firstResult();
    }

    public PanacheQuery<PixRecebimento> findByChave(String chave) {
        return find("chave like ?1", '%'+chave+"%");
    }

    public PanacheQuery<PixRecebimento> listAllInativo() {
        return find("ativo = ?1", false);
    }
}
