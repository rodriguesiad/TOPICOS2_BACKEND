package br.unitins.projeto.service.estado;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

import br.unitins.projeto.dto.estado.EstadoDTO;
import br.unitins.projeto.dto.estado.EstadoResponseDTO;
import br.unitins.projeto.model.Estado;
import br.unitins.projeto.repository.EstadoRepository;

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
    public EstadoResponseDTO create(EstadoDTO estadoDTO) throws ConstraintViolationException {
        validar(estadoDTO);

        Estado entity = new Estado();
        entity.setDescricao(estadoDTO.descricao());
        entity.setSigla(estadoDTO.sigla().toUpperCase());
        repository.persist(entity);

        return new EstadoResponseDTO(entity);
    }

    @Override
    @Transactional
    public EstadoResponseDTO update(Long id, EstadoDTO estadoDTO) throws ConstraintViolationException {
        validar(estadoDTO);

        Estado entity = repository.findById(id);
        entity.setDescricao(estadoDTO.descricao());
        entity.setSigla(estadoDTO.sigla().toUpperCase());

        return new EstadoResponseDTO(entity);
    }

    private void validar(EstadoDTO estadoDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<EstadoDTO>> violations = validator.validate(estadoDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<EstadoResponseDTO> findBySigla(String sigla) {
        List<Estado> list = repository.findBySigla(sigla);
        return list.stream().map(EstadoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public Long count() {
        return repository.count();
    }

}
