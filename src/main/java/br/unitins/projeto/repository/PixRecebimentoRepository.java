package br.unitins.projeto.repository;

import br.unitins.projeto.model.PixRecebimento;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PixRecebimentoRepository implements PanacheRepository<PixRecebimento> {

}
