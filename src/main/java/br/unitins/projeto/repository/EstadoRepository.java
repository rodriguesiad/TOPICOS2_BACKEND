package br.unitins.projeto.repository;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

import br.unitins.projeto.model.Estado;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class EstadoRepository implements PanacheRepository<Estado> {
    
    public List<Estado> findBySigla(String sigla){
        if (sigla == null)
            return null;
        return find("UPPER(sigla) LIKE ?1 ", "%"+sigla.toUpperCase()+"%").list();
    }

}
