package br.unitins.projeto.service.municipio;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
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
    public List<MunicipioResponseDTO> getAll(int page, int pageSize) {
        List<Municipio> list = repository.findAll().page(page, pageSize).list();
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
    public List<MunicipioResponseDTO> findByEstado(Long idEstado) {
        List<Municipio> list = repository.findByEstado(idEstado);
        return list.stream().map(MunicipioResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MunicipioResponseDTO create(@Valid MunicipioDTO municipioDTO) throws ConstraintViolationException {
        validar(municipioDTO);

        Municipio entity = new Municipio();
        entity.setNome(municipioDTO.nome());
        entity.setEstado(estadoRepository.findById(municipioDTO.idEstado()));
        repository.persist(entity);

        return new MunicipioResponseDTO(entity);
    }

    @Override
    @Transactional
    public MunicipioResponseDTO update(Long id, @Valid MunicipioDTO municipioDTO) throws ConstraintViolationException {
        validar(municipioDTO);

        Municipio entity = repository.findById(id);

        entity.setNome(municipioDTO.nome());
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
    public List<MunicipioResponseDTO> findByNome(String descricao, int page, int pageSize) {
        List<Municipio> list = repository.findByNome(descricao).page(page, pageSize).list();
        return list.stream().map(MunicipioResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public Long count() {
        return repository.count();
    }

    @Override
    public Long countByNome(String nome) {
        return repository.findByNome(nome).count();
    }

}
