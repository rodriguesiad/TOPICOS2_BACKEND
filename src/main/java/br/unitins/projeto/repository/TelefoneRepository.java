package br.unitins.projeto.repository;

import jakarta.enterprise.context.ApplicationScoped;

import br.unitins.projeto.model.Telefone;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class TelefoneRepository implements PanacheRepository<Telefone> {
}
