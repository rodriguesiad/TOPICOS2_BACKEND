package br.unitins.projeto.repository;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;

import br.unitins.projeto.model.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario> {

    public PanacheQuery<Usuario> findByCampoBusca(String campoBusca) {
        if (campoBusca == null)
            return null;

        return find("UPPER(pessoaFisica.nome) LIKE ?1 OR pessoaFisica.cpf LIKE ?1 ", "%" + campoBusca.toUpperCase() + "%");
    }

    public Usuario findByLoginAndSenha(String login, String senha) {
        if (login == null || senha == null)
            return null;

        return find("login = ?1 AND senha = ?2 ", login, senha).firstResult();
    }

    public Usuario findByLogin(String login) {
        if (login == null)
            return null;

        return find("login = ?1 ", login).firstResult();
    }

}
