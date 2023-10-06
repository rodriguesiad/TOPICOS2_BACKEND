package br.unitins.projeto.repository;

import br.unitins.projeto.model.BoletoRecebimento;
import br.unitins.projeto.model.PixRecebimento;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class BoletoRecebimentoRepository implements PanacheRepository<BoletoRecebimento> {

    public PanacheQuery<BoletoRecebimento> findByCNPJ(String cnpj) {
        if (cnpj == null)
            return null;
        return find("UPPER(cnpj) LIKE ?1 ", "%" + cnpj.toUpperCase() + "%");
    }

    public BoletoRecebimento findByAtivo(Boolean situacao) {
        if (situacao == null)
            return null;

        return find("ativo = ?1", situacao).firstResult();
    }

    public PanacheQuery<BoletoRecebimento> listAllInativo() {
        return find("ativo = ?1", false);
    }

}
