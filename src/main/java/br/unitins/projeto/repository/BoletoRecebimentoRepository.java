package br.unitins.projeto.repository;

import br.unitins.projeto.model.BoletoRecebimento;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class BoletoRecebimentoRepository implements PanacheRepository<BoletoRecebimento> {

    public List<BoletoRecebimento> findByCNPJ(String cnpj) {
        if (cnpj == null)
            return null;
        return find("UPPER(cnpj) LIKE ?1 ", "%" + cnpj.toUpperCase() + "%").list();
    }

}
