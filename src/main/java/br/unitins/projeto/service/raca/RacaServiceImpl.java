package br.unitins.projeto.service.raca;

import br.unitins.projeto.dto.raca.RacaDTO;
import br.unitins.projeto.dto.raca.RacaResponseDTO;
import br.unitins.projeto.dto.situacao.AlterarSituacaoDTO;
import br.unitins.projeto.model.Raca;
import br.unitins.projeto.repository.RacaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class RacaServiceImpl implements RacaService {

    @Inject
    RacaRepository repository;

    @Inject
    Validator validator;

    @Override
    public List<RacaResponseDTO> getAll() {
        List<Raca> list = repository.listAll();
        return list.stream().map(RacaResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public RacaResponseDTO findById(Long id) {
        Raca raca = repository.findById(id);

        if (raca == null)
            throw new NotFoundException("Raca não encontrado.");

        return new RacaResponseDTO(raca);
    }

    @Override
    @Transactional
    public RacaResponseDTO create(RacaDTO racaDto) throws ConstraintViolationException {
        validar(racaDto);

        Raca entity = new Raca();
        entity.setNome(racaDto.nome());
        repository.persist(entity);

        return new RacaResponseDTO(entity);
    }

    @Override
    @Transactional
    public RacaResponseDTO update(Long id, RacaDTO racaDto) throws ConstraintViolationException {
        validar(racaDto);

        Raca entity = repository.findById(id);
        entity.setNome(racaDto.nome());

        return new RacaResponseDTO(entity);
    }

    private void validar(RacaDTO racaDto) throws ConstraintViolationException {
        Set<ConstraintViolation<RacaDTO>> violations = validator.validate(racaDto);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<RacaResponseDTO> findByNome(String nome) {
        List<Raca> list = repository.findByNome(nome);
        return list.stream().map(RacaResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RacaResponseDTO alterarSituacao(Long id, AlterarSituacaoDTO dto) {
        Raca entity = repository.findById(id);
        entity.setAtivo(dto.situacao());

        return new RacaResponseDTO(entity);
    }

    @Override
    public Long count() {
        return repository.count();
    }

}
