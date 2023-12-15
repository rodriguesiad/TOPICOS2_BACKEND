package br.unitins.projeto.repository;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;

import br.unitins.projeto.model.Municipio;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class MunicipioRepository implements PanacheRepository<Municipio> {
    
    public PanacheQuery<Municipio> findByNome(String nome){
        if (nome == null)
            return null;
        return find("UPPER(nome) LIKE ?1 ", "%"+nome.toUpperCase()+"%");
    }

    public List<Municipio> findByEstado(Long idEstado) {
        if (idEstado == null)
            return null;
        return find("estado.id = ?1 ", idEstado).list();
    }
}
