package br.unitins.projeto.service.produto;

import br.unitins.projeto.dto.produto.ProdutoDTO;
import br.unitins.projeto.dto.produto.ProdutoResponseDTO;
import br.unitins.projeto.form.ProdutoImageForm;
import br.unitins.projeto.model.PorteAnimal;
import br.unitins.projeto.model.Produto;
import br.unitins.projeto.repository.CategoriaRepository;
import br.unitins.projeto.repository.EspecieRepository;
import br.unitins.projeto.repository.ProdutoRepository;
import br.unitins.projeto.repository.RacaRepository;
import br.unitins.projeto.service.file.FileService;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

import java.io.IOException;
import java.io.NotActiveException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProdutoServiceImpl implements ProdutoService {

    @Inject
    ProdutoRepository repository;

    @Inject
    RacaRepository racaRepository;

    @Inject
    EspecieRepository especieRepository;

    @Inject
    CategoriaRepository categoriaRepository;

    @Inject
    Validator validator;

    @Inject
    FileService fileService;

    @Override
    public List<ProdutoResponseDTO> getAll() {
        List<Produto> list = repository.listAll();
        return list.stream().map(ProdutoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public ProdutoResponseDTO findById(Long id) {
        Produto produto = repository.findById(id);

        if (produto == null)
            throw new NotFoundException("Produto não encontrado.");

        return new ProdutoResponseDTO(produto);
    }

    @Override
    @Transactional
    public ProdutoResponseDTO create(@Valid ProdutoDTO produtoDto) throws ConstraintViolationException {

        Produto entity = new Produto();
        entity.setNome(produtoDto.nome());
        entity.setDescricao(produtoDto.descricao());
        entity.setEstoque(produtoDto.estoque());
        entity.setAtivo(Boolean.TRUE);
        entity.setPeso(produtoDto.peso());
        entity.setPorteAnimal(PorteAnimal.valueOf(produtoDto.porte()));
        entity.setPreco(produtoDto.preco());
        entity.setCategoria(categoriaRepository.findById(produtoDto.idCategoria()));
        entity.setEspecie(especieRepository.findById(produtoDto.idEspecie()));
        entity.setRaca(racaRepository.findById(produtoDto.idRaca()));

        repository.persist(entity);
        return new ProdutoResponseDTO(entity);
    }

    @Override
    @Transactional
    public ProdutoResponseDTO update(Long id, @Valid ProdutoDTO produtoDto) throws ConstraintViolationException {

        Produto entity = repository.findById(id);

        if (entity == null){
            throw new NotFoundException("Produto não encontrado");
        }

        entity.setNome(produtoDto.nome());
        entity.setDescricao(produtoDto.descricao());
        entity.setEstoque(produtoDto.estoque());
        entity.setPeso(produtoDto.peso());
        entity.setPorteAnimal(PorteAnimal.valueOf(produtoDto.porte()));
        entity.setPreco(produtoDto.preco());
        entity.setCategoria(categoriaRepository.findById(produtoDto.idCategoria()));
        entity.setEspecie(especieRepository.findById(produtoDto.idEspecie()));
        entity.setRaca(racaRepository.findById(produtoDto.idRaca()));
        entity.setAtivo(entity.getAtivo());

        repository.persist(entity);

        return new ProdutoResponseDTO(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<ProdutoResponseDTO> findByNome(String nome, Boolean ativo, int pageNumber, int pageSize) {
        List<Produto> list = this.repository.findByFiltro(nome, ativo)
                .page(Page.of(pageNumber, pageSize))
                .list().stream()
                .sorted(Comparator.comparing(Produto::getNome))
                .collect(Collectors.toList());

        return list.stream().map(ProdutoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProdutoResponseDTO alterarSituacao(Long id, Boolean dto) {
        Produto entity = repository.findById(id);
        entity.setAtivo(dto);

        return new ProdutoResponseDTO(entity);
    }

    @Override
    public Long count() {
        return repository.count();
    }

    @Override
    public List<ProdutoResponseDTO> findAllPaginado(int pageNumber, int pageSize) {
        List<Produto> lista = this.repository.findAll()
                .page(Page.of(pageNumber, pageSize))
                .list();

        return lista.stream().map(ProdutoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public Long countByNome(String nome, Boolean ativo) {
        return repository.findByFiltro(nome, ativo).count();
    }

    @Override
    @Transactional
    public void salvarImagens(ProdutoImageForm imagem) throws IOException {
        Produto produto = repository.findById(imagem.getId());

        if (produto == null) {
            throw new NotActiveException("Produto não encontrado");
        }

        try {
            String novoNomeImagem = fileService.salvarImagem(imagem.getImagem(), imagem.getNomeImagem(), "produto");
            String imagemAntiga = produto.getNomeImagem();
            produto.setNomeImagem(novoNomeImagem);

            fileService.excluirImagem(imagemAntiga, "produto");
        } catch (IOException e) {
            throw e;
        }
    }

}
