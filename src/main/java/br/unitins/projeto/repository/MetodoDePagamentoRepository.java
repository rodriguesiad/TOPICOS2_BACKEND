package br.unitins.projeto.repository;

import br.unitins.projeto.model.MetodoDePagamento;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MetodoDePagamentoRepository implements PanacheRepository<MetodoDePagamento> {

}