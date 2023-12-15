package br.unitins.projeto.repository;

import br.unitins.projeto.model.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario> {

    public PanacheQuery<Usuario> findByCampoBusca(String campoBusca, Boolean ativo) {
        if (campoBusca != null && ativo != null) {
            return find("(UPPER(pessoaFisica.nome) LIKE ?1 OR pessoaFisica.cpf LIKE ?1)  AND ativo = ?2 ", "%" + campoBusca.toUpperCase() + "%", ativo);
        }

        if (campoBusca != null && ativo == null) {
            return find("UPPER(pessoaFisica.nome) LIKE ?1 OR pessoaFisica.cpf LIKE ?1 ", "%" + campoBusca.toUpperCase() + "%");
        }

        if (campoBusca == null && ativo != null) {
            return find("ativo = ?1 ", "%" + campoBusca.toUpperCase() + "%", ativo);
        }

        return null;
    }

    public Usuario findByLoginAndSenha(String login, String senha) {
        if (login == null || senha == null)
            return null;

        return find("login = ?1 AND senha = ?2 AND ativo = true ", login, senha).firstResult();
    }

    public Usuario findByLogin(String login) {
        if (login == null)
            return null;

        return find("login = ?1 ", login).firstResult();
    }

}
