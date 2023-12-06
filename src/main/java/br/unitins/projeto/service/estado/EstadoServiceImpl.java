package br.unitins.projeto.service.estado;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import br.unitins.projeto.dto.estado.EstadoDTO;
import br.unitins.projeto.dto.estado.EstadoResponseDTO;
import br.unitins.projeto.model.Estado;
import br.unitins.projeto.repository.EstadoRepository;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class EstadoServiceImpl implements EstadoService {

    @Inject
    EstadoRepository repository;

    @Inject
    Validator validator;

    @Override
    public List<EstadoResponseDTO> getAll() {
        List<Estado> list = repository.listAll();
        return list.stream().map(EstadoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public EstadoResponseDTO findById(Long id) {
        Estado estado = repository.findById(id);

        if (estado == null)
            throw new NotFoundException("Estado não encontrado.");

        return new EstadoResponseDTO(estado);
    }

    @Override
    @Transactional
    public EstadoResponseDTO create(@Valid EstadoDTO estadoDTO) throws ConstraintViolationException {

        Estado entity = new Estado();
        entity.setNome(estadoDTO.nome());
        entity.setSigla(estadoDTO.sigla().toUpperCase());
        repository.persist(entity);

        return new EstadoResponseDTO(entity);
    }

    @Override
    @Transactional
    public EstadoResponseDTO update(Long id, @Valid EstadoDTO estadoDTO) throws ConstraintViolationException {

        Estado entity = repository.findById(id);
        entity.setNome(estadoDTO.nome());
        entity.setSigla(estadoDTO.sigla().toUpperCase());

        return new EstadoResponseDTO(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<EstadoResponseDTO> findByFiltro(String nome, String situacao, int pageNumber, int pageSize) {
        Boolean ativo = null;

        if (situacao.equals("Inativo")){
            ativo = false;
        } else if (situacao.equals("Ativo")) {
            ativo = true;
        }

        List<Estado> list = this.repository.findByFiltro(nome, ativo)
                .page(Page.of(pageNumber, pageSize))
                .list().stream()
                .sorted(Comparator.comparing(Estado::getNome))
                .collect(Collectors.toList());

        return list.stream().map(EstadoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public Long count() {
        return repository.count();
    }

    @Override
    public List<EstadoResponseDTO> findAllPaginado(int pageNumber, int pageSize) {
        List<Estado> lista = this.repository.findAll()
                .page(Page.of(pageNumber, pageSize))
                .list();

        return lista.stream().map(EstadoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public Long countByFiltro(String nome, String situacao) {
        Boolean ativo = null;

        if (situacao.equals("Inativo")){
            ativo = false;
        } else if (situacao.equals("Ativo")) {
            ativo = true;
        }

        return this.repository.findByFiltro(nome, ativo).count();
    }
}