package br.unitins.projeto.service.produto;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.unitins.projeto.dto.produto.ProdutoDTO;
import br.unitins.projeto.dto.produto.ProdutoResponseDTO;
import br.unitins.projeto.model.PorteAnimal;
import br.unitins.projeto.model.Produto;
import br.unitins.projeto.repository.CategoriaRepository;
import br.unitins.projeto.repository.EspecieRepository;
import br.unitins.projeto.repository.ProdutoRepository;
import br.unitins.projeto.repository.RacaRepository;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

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
    public ProdutoResponseDTO create(ProdutoDTO produtoDto) throws ConstraintViolationException {
        validar(produtoDto);

        Produto entity = new Produto();
        entity.setNome(produtoDto.nome());
        entity.setDescricao(produtoDto.descricao());
        entity.setEstoque(produtoDto.estoque());
        entity.setAtivo(true);
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
    public ProdutoResponseDTO update(Long id, ProdutoDTO produtoDto) throws ConstraintViolationException {
        validar(produtoDto);

        Produto entity = repository.findById(id);
        entity.setNome(produtoDto.nome());
        entity.setDescricao(produtoDto.descricao());
        entity.setEstoque(produtoDto.estoque());
        entity.setAtivo(produtoDto.ativo());
        entity.setPeso(produtoDto.peso());
        entity.setPorteAnimal(PorteAnimal.valueOf(produtoDto.porte()));
        entity.setPreco(produtoDto.preco());
        entity.setCategoria(categoriaRepository.findById(produtoDto.idCategoria()));
        entity.setEspecie(especieRepository.findById(produtoDto.idEspecie()));
        entity.setRaca(racaRepository.findById(produtoDto.idRaca()));

        repository.persist(entity);

        return new ProdutoResponseDTO(entity);
    }

    private void validar(ProdutoDTO produtoDto) throws ConstraintViolationException {
        Set<ConstraintViolation<ProdutoDTO>> violations = validator.validate(produtoDto);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<ProdutoResponseDTO> findByNome(String nome, String situacao, int pageNumber, int pageSize) {
        Boolean ativo = null;

        if (situacao.equals("Inativo")){
            ativo = false;
        } else if (situacao.equals("Ativo")) {
            ativo = true;
        }

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
    public Long countByNome(String nome, String situacao) {
        Boolean ativo = null;

        if (situacao.equals("Inativo")){
            ativo = false;
        } else if (situacao.equals("Ativo")) {
            ativo = true;
        }

        return repository.findByFiltro(nome, ativo).count();
    }
}
