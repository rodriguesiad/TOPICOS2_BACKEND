package br.unitins.projeto.service.categoria;

import br.unitins.projeto.dto.categoria.CategoriaDTO;
import br.unitins.projeto.dto.categoria.CategoriaResponseDTO;
import br.unitins.projeto.dto.situacao.AlterarSituacaoDTO;
import br.unitins.projeto.model.Categoria;
import br.unitins.projeto.repository.CategoriaRepository;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class CategoriaServiceImpl implements CategoriaService {

    @Inject
    CategoriaRepository repository;

    @Inject
    Validator validator;

    @Override
    public List<CategoriaResponseDTO> getAll() {
        List<Categoria> list = repository.listAll();
        return list.stream().map(CategoriaResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public CategoriaResponseDTO findById(Long id) {
        Categoria categoria = repository.findById(id);

        if (categoria == null)
            throw new NotFoundException("Categoria não encontrado.");

        return new CategoriaResponseDTO(categoria);
    }

    @Override
    @Transactional
    public CategoriaResponseDTO create(CategoriaDTO categoriaDto) throws ConstraintViolationException {
        validar(categoriaDto);

        Categoria entity = new Categoria();
        entity.setNome(categoriaDto.nome());
        entity.setAtivo(true);
        repository.persist(entity);

        return new CategoriaResponseDTO(entity);
    }

    @Override
    @Transactional
    public CategoriaResponseDTO update(Long id, CategoriaDTO categoriaDto) throws ConstraintViolationException {
        validar(categoriaDto);

        Categoria entity = repository.findById(id);
        entity.setNome(categoriaDto.nome());

        return new CategoriaResponseDTO(entity);
    }

    private void validar(CategoriaDTO categoriaDto) throws ConstraintViolationException {
        Set<ConstraintViolation<CategoriaDTO>> violations = validator.validate(categoriaDto);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<CategoriaResponseDTO> findByNome(String nome) {
        List<Categoria> list = repository.findByNome(nome);
        return list.stream().map(CategoriaResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CategoriaResponseDTO alterarSituacao(Long id, Boolean situacao) {
        Categoria entity = repository.findById(id);
        entity.setAtivo(situacao);

        return new CategoriaResponseDTO(entity);
    }

    @Override
    public Long count() {
        return repository.count();
    }

    @Override
    public List<CategoriaResponseDTO> findAllPaginado(int pageNumber, int pageSize) {
        List<Categoria> lista = this.repository.findAll()
                .page(Page.of(pageNumber, pageSize))
                .list().stream()
                .sorted(Comparator.comparing(Categoria::getNome))
                .collect(Collectors.toList());

        return lista.stream().map(CategoriaResponseDTO::new).collect(Collectors.toList());
    }

}
