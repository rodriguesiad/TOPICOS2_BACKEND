package br.unitins.projeto.repository;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

import br.unitins.projeto.model.EnderecoCompra;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class EnderecoCompraRepository implements PanacheRepository<EnderecoCompra> {
    
    public List<EnderecoCompra> findByCEP(String cep){
        if (cep == null)
            return null;
        return find("UPPER(cep) LIKE ?1 ", "%"+cep.toUpperCase()+"%").list();
    }

}
