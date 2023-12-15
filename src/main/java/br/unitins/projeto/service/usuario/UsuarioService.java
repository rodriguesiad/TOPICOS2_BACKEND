package br.unitins.projeto.service.usuario;

import br.unitins.projeto.dto.endereco.EnderecoDTO;
import br.unitins.projeto.dto.endereco.EnderecoResponseDTO;
import br.unitins.projeto.dto.endereco.EnderecoUpdateDTO;
import br.unitins.projeto.dto.usuario.UsuarioDTO;
import br.unitins.projeto.dto.usuario.UsuarioResponseDTO;
import br.unitins.projeto.dto.usuario.cadastro.CadastroAdminDTO;
import br.unitins.projeto.dto.usuario.cadastro.CadastroAdminResponseDTO;
import br.unitins.projeto.dto.usuario.cadastro.CadastroDTO;
import br.unitins.projeto.dto.usuario.dados_pessoais.DadosPessoaisDTO;
import br.unitins.projeto.dto.usuario.dados_pessoais.DadosPessoaisResponseDTO;
import br.unitins.projeto.dto.usuario.enderecos.UsuarioEnderecoResponseDTO;
import br.unitins.projeto.dto.usuario.senha.SenhaDTO;
import br.unitins.projeto.dto.usuario.telefone.UsuarioTelefoneDTO;
import br.unitins.projeto.dto.usuario.telefone.UsuarioTelefoneResponseDTO;
import br.unitins.projeto.model.Usuario;
import jakarta.validation.Valid;

import java.util.List;

public interface UsuarioService {

    List<UsuarioResponseDTO> getAll();

    UsuarioResponseDTO findById(Long id);

    UsuarioResponseDTO create(@Valid CadastroDTO productDTO);

    void delete(Long id);

    List<CadastroAdminResponseDTO> findByCampoBusca(String campoBusca, String situacao, int pageNumber, int pageSize);

    Long count();

    Long countByCampoBusca(String campoBusca, String situacao);

    Usuario findByLoginAndSenha(String login, String senha);

    UsuarioResponseDTO findByLogin(String login);

    DadosPessoaisResponseDTO getDadosPessoais(Long id);

    DadosPessoaisResponseDTO updateDadosPessoais(Long id, @Valid DadosPessoaisDTO dto);

    Boolean updateSenha(Long id, @Valid SenhaDTO senha);

    UsuarioEnderecoResponseDTO getEnderecos(Long id);

    EnderecoResponseDTO insertEndereco(Long id, @Valid EnderecoDTO dto);

    UsuarioEnderecoResponseDTO updateEndereco(Long id, Long idEndereco, @Valid EnderecoUpdateDTO dto);

    void deleteEndereco(Long id, Long idEndereco);

    UsuarioTelefoneResponseDTO getTelefone(Long id);

    UsuarioTelefoneResponseDTO updateTelefone(Long id, @Valid UsuarioTelefoneDTO dto);

    UsuarioResponseDTO update(Long id, String nomeImagem);

    CadastroAdminResponseDTO cadastrarPorAdmin(@Valid CadastroAdminDTO dto);

    CadastroAdminResponseDTO alterarPorAdmin(Long id, @Valid CadastroAdminDTO dto);

    CadastroAdminResponseDTO alterarSituacao(Long id, Boolean situacao);

    List<CadastroAdminResponseDTO> findAllAdminPaginado(int pageNumber, int pageSize);

    CadastroAdminResponseDTO findByIdPorAdmin(Long id);

    byte[] criarRelatorioUsuarios();

}
