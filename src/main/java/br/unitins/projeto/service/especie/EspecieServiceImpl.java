package br.unitins.projeto.service.especie;

import br.unitins.projeto.dto.especie.EspecieDTO;
import br.unitins.projeto.dto.especie.EspecieResponseDTO;
import br.unitins.projeto.model.Especie;
import br.unitins.projeto.repository.EspecieRepository;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class EspecieServiceImpl implements EspecieService {

    @Inject
    EspecieRepository repository;

    @Inject
    Validator validator;

    @Override
    public List<EspecieResponseDTO> getAll() {
        List<Especie> list = repository.listAll();
        return list.stream().map(EspecieResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public EspecieResponseDTO findById(Long id) {
        Especie especie = repository.findById(id);

        if (especie == null)
            throw new NotFoundException("Especie não encontrado.");

        return new EspecieResponseDTO(especie);
    }

    @Override
    @Transactional
    public EspecieResponseDTO create(@Valid EspecieDTO especieDto) throws ConstraintViolationException {

        Especie entity = new Especie();
        entity.setNome(especieDto.nome());
        entity.setAtivo(true);
        repository.persist(entity);

        return new EspecieResponseDTO(entity);
    }

    @Override
    @Transactional
    public EspecieResponseDTO update(Long id, @Valid EspecieDTO especieDto) throws ConstraintViolationException {

        Especie entity = repository.findById(id);
        entity.setNome(especieDto.nome());
        entity.setAtivo(true);

        return new EspecieResponseDTO(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public EspecieResponseDTO alterarSituacao(Long id, Boolean situacao) {
        Especie entity = repository.findById(id);
        entity.setAtivo(situacao);

        return new EspecieResponseDTO(entity);
    }

    @Override
    public Long count() {
        return repository.count();
    }

    @Override
    public List<EspecieResponseDTO> findAllPaginado(int pageNumber, int pageSize) {
        List<Especie> lista = this.repository.findAll()
                .page(Page.of(pageNumber, pageSize))
                .list();

        return lista.stream().map(EspecieResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public Long countByNome(String nome,Boolean ativo) {
        return repository.findByFiltro(nome, ativo).count();
    }

    @Override
    public List<EspecieResponseDTO> findByNome(String nome, Boolean ativo, int pageNumber, int pageSize) {
        List<Especie> list = this.repository.findByFiltro(nome, ativo)
                .page(Page.of(pageNumber, pageSize))
                .list().stream()
                .sorted(Comparator.comparing(Especie::getNome))
                .collect(Collectors.toList());

        return list.stream().map(EspecieResponseDTO::new).collect(Collectors.toList());
    }
}
