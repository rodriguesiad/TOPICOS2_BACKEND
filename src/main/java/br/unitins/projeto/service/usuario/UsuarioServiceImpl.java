package br.unitins.projeto.service.usuario;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

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
import br.unitins.projeto.model.DefaultEntity;
import br.unitins.projeto.model.Endereco;
import br.unitins.projeto.model.Perfil;
import br.unitins.projeto.model.PessoaFisica;
import br.unitins.projeto.model.Produto;
import br.unitins.projeto.model.Telefone;
import br.unitins.projeto.model.Usuario;
import br.unitins.projeto.repository.CompraRepository;
import br.unitins.projeto.repository.ProdutoRepository;
import br.unitins.projeto.repository.UsuarioRepository;
import br.unitins.projeto.service.endereco.EnderecoService;
import br.unitins.projeto.service.hash.HashService;
import br.unitins.projeto.service.telefone.TelefoneService;
import br.unitins.projeto.validation.ValidationException;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    UsuarioRepository repository;

    @Inject
    TelefoneService telefoneService;

    @Inject
    EnderecoService enderecoService;

    @Inject
    Validator validator;

    @Inject
    HashService hashService;

    @Inject
    ProdutoRepository produtoRepository;

    @Inject
    CompraRepository compraRepository;

    @Override
    public List<UsuarioResponseDTO> getAll() {
        List<Usuario> list = repository.listAll();
        return list.stream().map(usuario -> UsuarioResponseDTO.valueOf(usuario)).collect(Collectors.toList());
    }

    @Override
    public UsuarioResponseDTO findById(Long id) {
        Usuario usuario = this.getUsuario(id);

        return UsuarioResponseDTO.valueOf(usuario);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO create(@Valid CadastroDTO usuarioDTO) throws ConstraintViolationException {

        Usuario entity = new Usuario();
        PessoaFisica pessoa = new PessoaFisica();

        pessoa.setNome(usuarioDTO.nome());
        pessoa.setEmail(usuarioDTO.email());
        pessoa.setCpf(usuarioDTO.cpf());
        pessoa.setDataNascimento(usuarioDTO.dataNascimento());

        entity.setLogin(usuarioDTO.email());
        entity.setSenha(hashService.getHashSenha(usuarioDTO.senha()));
        entity.setAtivo(true);

        if (usuarioDTO.enderecos() != null) {
            List<Endereco> enderecosModel = usuarioDTO.enderecos().stream().map(enderecoDTO -> {
                return enderecoService.toModel(enderecoDTO);
            }).collect(Collectors.toList());

            entity.setListaEndereco(enderecosModel);
        }
        entity.setPessoaFisica(pessoa);

        List<Perfil> perfis = new ArrayList<>();
        perfis.add(Perfil.COMUM);
        entity.setPerfis(perfis);

        repository.persist(entity);

        return UsuarioResponseDTO.valueOf(entity);
    }


    private void validar(UsuarioDTO usuarioDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<UsuarioDTO>> violations = validator.validate(usuarioDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<CadastroAdminResponseDTO> findByCampoBusca(String campoBusca, String situacao, int pageNumber, int pageSize) {
        Boolean ativo = null;

        if (situacao.equals("Inativo")) {
            ativo = false;
        } else if (situacao.equals("Ativo")) {
            ativo = true;
        }

        List<Usuario> list = repository.findByCampoBusca(campoBusca, ativo)
                .page(Page.of(pageNumber, pageSize))
                .list().stream()
                .sorted(Comparator.comparing(u -> u.getPessoaFisica().getNome()))
                .collect(Collectors.toList());

        return list.stream().map(usuario -> CadastroAdminResponseDTO.valueOf(usuario)).collect(Collectors.toList());

    }

    @Override
    public Long countByCampoBusca(String campoBusca, String situacao) {
        Boolean ativo = null;

        if (situacao.equals("Inativo")) {
            ativo = false;
        } else if (situacao.equals("Ativo")) {
            ativo = true;
        }

        return repository.findByCampoBusca(campoBusca, ativo).count();
    }


    @Override
    public Long count() {
        return repository.count();
    }

    @Override
    public Usuario findByLoginAndSenha(String login, String senha) {
        return repository.findByLoginAndSenha(login, senha);
    }

    @Override
    public UsuarioResponseDTO findByLogin(String login) {
        Usuario usuario = repository.findByLogin(login);
        if (usuario == null)
            throw new NotFoundException("Usuário não encontrado.");
        return UsuarioResponseDTO.valueOf(usuario);
    }

    @Override
    public DadosPessoaisResponseDTO getDadosPessoais(Long id) {
        Usuario usuario = this.getUsuario(id);

        return DadosPessoaisResponseDTO.valueOf(usuario);
    }

    @Override
    @Transactional
    public DadosPessoaisResponseDTO updateDadosPessoais(Long id, @Valid DadosPessoaisDTO dto) {
        Usuario usuario = this.getUsuario(id);

        try {
            PessoaFisica pessoa = usuario.getPessoaFisica();
            pessoa.setDataNascimento(dto.dataNascimento());
            pessoa.setNome(dto.nome());
            pessoa.setCpf(dto.cpf());

            if (dto.telefones() != null) {
                List<Telefone> telefonesModel = dto.telefones().stream().map(telefoneDTO -> {
                    return telefoneService.toModel(telefoneDTO);
                }).collect(Collectors.toList());

                usuario.setListaTelefone(telefonesModel);
            }

            usuario.setPessoaFisica(pessoa);

            return DadosPessoaisResponseDTO.valueOf(usuario);
        } catch (NullPointerException e) {
            throw new NullPointerException("O usuário não possui dados pessoais");
        }
    }

    @Override
    @Transactional
    public Boolean updateSenha(Long id, @Valid SenhaDTO dto) {
        Usuario usuario = this.getUsuario(id);

        try {
            if (usuario.getSenha().equals(hashService.getHashSenha(dto.senhaAtual()))) {
                usuario.setSenha(hashService.getHashSenha(dto.novaSenha()));
                return true;
            } else {
                throw new Exception("Senha Incorreta");
            }
        } catch (NullPointerException e) {
            throw new NullPointerException("O usuário não possui dados pessoais");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UsuarioEnderecoResponseDTO getEnderecos(Long id) {
        Usuario usuario = this.getUsuario(id);

        return UsuarioEnderecoResponseDTO.valueOf(usuario);
    }


    @Override
    @Transactional
    public EnderecoResponseDTO insertEndereco(Long id, @Valid EnderecoDTO dto) {
        Usuario usuario = this.getUsuario(id);

        if (usuario.getListaEndereco().isEmpty()) {
            usuario.setListaEndereco(new ArrayList<>());
        }

        Endereco endereco = enderecoService.toModel(dto);
        endereco.setUsuario(usuario);
        enderecoService.create(endereco);

        if(endereco.getPrincipal()) {
            usuario.getListaEndereco().forEach(end -> end.setPrincipal(false));
        }

        usuario.getListaEndereco().add(endereco);

        return new EnderecoResponseDTO(endereco);
    }

    @Override
    @Transactional
    public UsuarioEnderecoResponseDTO updateEndereco(Long id, Long idEndereco, @Valid EnderecoUpdateDTO dto) {
        Usuario usuario = this.getUsuario(id);

        if (usuario.getListaEndereco().isEmpty()) {
            throw new NotFoundException("O usuário não possuiu endereços cadastrados.");
        }

        int index = usuario.getListaEndereco().stream()
                .map(DefaultEntity::getId)
                .toList().indexOf(idEndereco);

        if (index == -1)
            throw new NotFoundException("Endereço não encontrado");

        Endereco enderecoModel = enderecoService.toUpdateModel(dto);
        enderecoService.update(dto.id(), dto);
        usuario.getListaEndereco().set(index, enderecoModel);

        return UsuarioEnderecoResponseDTO.valueOf(usuario);
    }

    @Override
    @Transactional
    public void deleteEndereco(Long id, Long idEndereco) {
        Usuario usuario = this.getUsuario(id);

        if (usuario.getListaEndereco().isEmpty()) {
            throw new NotFoundException("O usuário não possuiu endereços cadastrados.");
        }

        int index = usuario.getListaEndereco().stream()
                .map(DefaultEntity::getId)
                .toList().indexOf(idEndereco);

        if (index == -1)
            throw new NotFoundException("Endereço não encontrado");

        enderecoService.delete(idEndereco);
        usuario.getListaEndereco().remove(index);
    }

    @Override
    public UsuarioTelefoneResponseDTO getTelefone(Long id) {
        Usuario usuario = this.getUsuario(id);

        return UsuarioTelefoneResponseDTO.valueOf(usuario);
    }

    @Override
    @Transactional
    public UsuarioTelefoneResponseDTO updateTelefone(Long id, @Valid UsuarioTelefoneDTO dto) {
        Usuario usuario = this.getUsuario(id);

        try {

            List<Telefone> telefonesModel = dto.telefones().stream().map(telefoneDTO -> {
                return telefoneService.toModel(telefoneDTO);
            }).collect(Collectors.toList());

            usuario.setListaTelefone(telefonesModel);

            return UsuarioTelefoneResponseDTO.valueOf(usuario);
        } catch (NullPointerException e) {
            throw new NullPointerException("O usuário não possui dados pessoais");
        }
    }

    @Override
    @Transactional
    public UsuarioResponseDTO update(Long id, String nomeImagem) {
        Usuario entity = repository.findById(id);
        entity.setNomeImagem(nomeImagem);

        return UsuarioResponseDTO.valueOf(entity);
    }

    @Override
    @Transactional
    public CadastroAdminResponseDTO cadastrarPorAdmin(@Valid CadastroAdminDTO dto) {

        if (repository.findByLogin(dto.email()) != null) {
            throw new ValidationException("login", "O login informado já existe, informe outro login.");
        }

        Usuario entity = new Usuario();
        PessoaFisica pessoa = new PessoaFisica();

        pessoa.setNome(dto.nome());
        pessoa.setEmail(dto.email());
        pessoa.setCpf(dto.cpf());
        pessoa.setDataNascimento(dto.dataNascimento());

        entity.setLogin(dto.email());
        entity.setSenha(hashService.getHashSenha(dto.senha()));

        if (dto.telefones() != null) {
            List<Telefone> telefonesModel = dto.telefones().stream().map(telefoneDTO -> {
                return telefoneService.toModel(telefoneDTO);
            }).collect(Collectors.toList());

            entity.setListaTelefone(telefonesModel);
        }


        entity.setPessoaFisica(pessoa);

        List<Perfil> perfis = new ArrayList<>();

        if (dto.perfis() != null) {
            for (Integer idPerfil : dto.perfis()) {
                if (!Perfil.valueOf(idPerfil).equals(Perfil.COMUM)) {
                    perfis.add(Perfil.valueOf(idPerfil));
                }
            }
        }

        perfis.add(Perfil.COMUM);
        entity.setPerfis(perfis);

        entity.setAtivo(true);

        repository.persist(entity);

        return CadastroAdminResponseDTO.valueOf(entity);
    }


    @Override
    @Transactional
    public CadastroAdminResponseDTO alterarPorAdmin(Long id, @Valid CadastroAdminDTO dto) {

        Usuario entity = repository.findById(id);

        if (entity != null) {
            PessoaFisica pessoa = entity.getPessoaFisica();

            pessoa.setNome(dto.nome());
            pessoa.setEmail(dto.email());
            pessoa.setCpf(dto.cpf());
            pessoa.setDataNascimento(dto.dataNascimento());

            entity.setLogin(dto.email());

            if (!dto.senha().equals(entity.getSenha())) {
                entity.setSenha(hashService.getHashSenha(dto.senha()));
            }

            if (dto.telefones() != null) {
                List<Telefone> telefonesModel = dto.telefones().stream().map(telefoneDTO -> {
                    return telefoneService.toModel(telefoneDTO);
                }).collect(Collectors.toList());

                entity.setListaTelefone(telefonesModel);
            }


            entity.setPessoaFisica(pessoa);

            List<Perfil> perfis = new ArrayList<>();

            if (dto.perfis() != null) {
                for (Integer idPerfil : dto.perfis()) {
                    if (!Perfil.valueOf(idPerfil).equals(Perfil.COMUM)) {
                        perfis.add(Perfil.valueOf(idPerfil));
                    }
                }
            }

            perfis.add(Perfil.COMUM);
            entity.setPerfis(perfis);

            return CadastroAdminResponseDTO.valueOf(entity);
        }

        return CadastroAdminResponseDTO.valueOf(entity);
    }

    @Override
    @Transactional
    public CadastroAdminResponseDTO alterarSituacao(Long id, Boolean situacao) {
        Usuario entity = repository.findById(id);
        entity.setAtivo(situacao);

        return CadastroAdminResponseDTO.valueOf(entity);
    }

    @Override
    public List<CadastroAdminResponseDTO> findAllAdminPaginado(int pageNumber, int pageSize) {
        List<Usuario> lista = this.repository.findAll()
                .page(Page.of(pageNumber, pageSize))
                .list().stream()
                .sorted(Comparator.comparing(u -> u.getPessoaFisica().getNome()))
                .collect(Collectors.toList());

        return lista.stream().map(usuario -> CadastroAdminResponseDTO.valueOf(usuario)).collect(Collectors.toList());
    }

    @Override
    public CadastroAdminResponseDTO findByIdPorAdmin(Long id) {
        Usuario usuario = this.getUsuario(id);

        return CadastroAdminResponseDTO.valueOf(usuario);
    }

    private Usuario getUsuario(Long id) {
        Usuario usuario = repository.findById(id);

        if (usuario == null)
            throw new NotFoundException("Usuário não encontrado.");

        return usuario;
    }

    @Override
    public byte[] criarRelatorioUsuarios() {

        List<Usuario> lista = repository.findAll().list();
        return gerarRelatorioPDF(lista);
    }

    private byte[] gerarRelatorioPDF(List<Usuario> usuarios){

         // Crie um ByteArrayOutputStream para armazenar o PDF resultante
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // Crie um documento PDF usando o iText7
        try (PdfDocument pdfDocument = new PdfDocument(new PdfWriter(baos))) {

            Document document = new Document(pdfDocument, PageSize.A4);
            pdfDocument.addEventHandler(PdfDocumentEvent.END_PAGE, new HeaderFooterHandler());

            // Adicione um cabeçalho ao PDF
            // Image logo = new Image(ImageDataFactory.create("caminho/para/sua/logo.png"));
            // document.add(logo);


            // Adicione um título e um subtítulo
            Paragraph titulo = new Paragraph("Relatório de Usuários")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(22);

            String dataHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm"));
            Paragraph subtitulo = new Paragraph("Gerado em: " + dataHora)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(12);
            document.add(titulo);
            document.add(subtitulo);

            // Adicione a tabela com os itens
            Table tabela = new Table(new float[]{1, 2, 1, 1})
                    .setWidth(UnitValue.createPercentValue(100))
                    .setMarginTop(10);
            tabela.addHeaderCell("ID");
            tabela.addHeaderCell("Nome");
            tabela.addHeaderCell("Ativo");
            tabela.addHeaderCell("Compras");

            for (Usuario usuario : usuarios) {

            tabela.addCell(String.valueOf(usuario.getId()));
            tabela.addCell(usuario.getPessoaFisica().getNome());

            if (usuario.getAtivo()==false) {
                tabela.addCell("Não");
            }
            else{
                tabela.addCell("Sim");
            }

            int numeroCompras = (int) compraRepository.findByUsuario(usuario.getId()).count();
            tabela.addCell(String.valueOf(numeroCompras));
            }

            document.add(tabela);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return baos.toByteArray();
    }

    class HeaderFooterHandler implements IEventHandler {
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdf = docEvent.getDocument();
            PdfPage page = docEvent.getPage();
            int pageNum = pdf.getPageNumber(page);

            PdfCanvas canvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdf);
            canvas.beginText().setFontAndSize(pdf.getDefaultFont(), 12);

            canvas.moveText(34, 20).showText("Página "+ pageNum);

            String dataHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm:ss"));
            canvas.moveText(500 - 80, 0).showText(dataHora);

            canvas.endText();

        }
    }
}
