package br.unitins.projeto.service.telefone;

import br.unitins.projeto.dto.telefone.TelefoneDTO;
import br.unitins.projeto.dto.telefone.TelefoneResponseDTO;
import br.unitins.projeto.model.Telefone;
import br.unitins.projeto.repository.TelefoneRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class TelefoneServiceImpl implements TelefoneService {

    @Inject
    TelefoneRepository repository;

    @Inject
    Validator validator;

    @Override
    public List<TelefoneResponseDTO> getAll() {
        List<Telefone> list = repository.listAll();
        return list.stream().map(TelefoneResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public TelefoneResponseDTO findById(Long id) {
        Telefone telefone = repository.findById(id);

        if (telefone == null)
            throw new NotFoundException("Telefone não encontrado.");

        return new TelefoneResponseDTO(telefone);
    }

    @Override
    @Transactional
    public TelefoneResponseDTO create(TelefoneDTO telefoneDTO) throws ConstraintViolationException {
        validar(telefoneDTO);

        Telefone entity = new Telefone();
        entity.setCodigoArea(telefoneDTO.codigoArea());
        entity.setNumero(telefoneDTO.numero().toUpperCase());

        repository.persist(entity);

        return new TelefoneResponseDTO(entity);
    }

    @Override
    @Transactional
    public TelefoneResponseDTO update(Long id, TelefoneDTO telefoneDTO) throws ConstraintViolationException {
        validar(telefoneDTO);

        Telefone entity = repository.findById(id);
        entity.setCodigoArea(telefoneDTO.codigoArea());
        entity.setNumero(telefoneDTO.numero().toUpperCase());

        return new TelefoneResponseDTO(entity);
    }

    @Override
    public Telefone toModel(@Valid TelefoneDTO telefoneDTO) {
        Telefone entity = new Telefone();

        entity.setNumero(telefoneDTO.numero());
        entity.setCodigoArea(telefoneDTO.codigoArea());

        return entity;
    }

    private void validar(TelefoneDTO telefoneDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<TelefoneDTO>> violations = validator.validate(telefoneDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Long count() {
        return repository.count();
    }

}
