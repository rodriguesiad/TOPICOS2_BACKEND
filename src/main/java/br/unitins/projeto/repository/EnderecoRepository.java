package br.unitins.projeto.repository;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

import br.unitins.projeto.model.Endereco;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class EnderecoRepository implements PanacheRepository<Endereco> {
    
    public List<Endereco> findByCEP(String cep){
        if (cep == null)
            return null;
        return find("UPPER(cep) LIKE ?1 ", "%"+cep.toUpperCase()+"%").list();
    }

}
