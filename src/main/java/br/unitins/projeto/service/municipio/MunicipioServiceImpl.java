package br.unitins.projeto.service.municipio;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.unitins.projeto.service.estado.EstadoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

import br.unitins.projeto.dto.municipio.MunicipioDTO;
import br.unitins.projeto.dto.municipio.MunicipioResponseDTO;
import br.unitins.projeto.model.Municipio;
import br.unitins.projeto.repository.EstadoRepository;
import br.unitins.projeto.repository.MunicipioRepository;

@ApplicationScoped
public class MunicipioServiceImpl implements MunicipioService {

    @Inject
    MunicipioRepository repository;

    @Inject
    EstadoRepository estadoRepository;

    @Inject
    Validator validator;

    @Override
    public List<MunicipioResponseDTO> getAll() {
        List<Municipio> list = repository.listAll();
        return list.stream().map(MunicipioResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public MunicipioResponseDTO findById(Long id) {
        Municipio municipio = repository.findById(id);

        if (municipio == null)
            throw new NotFoundException("Município não encontrado.");

        return new MunicipioResponseDTO(municipio);
    }

    @Override
    @Transactional
    public MunicipioResponseDTO create(MunicipioDTO municipioDTO) throws ConstraintViolationException {
        validar(municipioDTO);

        Municipio entity = new Municipio();
        entity.setDescricao(municipioDTO.descricao());
        entity.setEstado(estadoRepository.findById(municipioDTO.idEstado()));
        repository.persist(entity);

        return new MunicipioResponseDTO(entity);
    }

    @Override
    @Transactional
    public MunicipioResponseDTO update(Long id, MunicipioDTO municipioDTO) throws ConstraintViolationException {
        validar(municipioDTO);

        Municipio entity = repository.findById(id);

        entity.setDescricao(municipioDTO.descricao());
        entity.setEstado(estadoRepository.findById(municipioDTO.idEstado()));

        return new MunicipioResponseDTO(entity);
    }

    private void validar(MunicipioDTO municipioDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<MunicipioDTO>> violations = validator.validate(municipioDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<MunicipioResponseDTO> findByDescricao(String descricao) {
        List<Municipio> list = repository.findByDescricao(descricao);
        return list.stream().map(MunicipioResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public Long count() {
        return repository.count();
    }

}
