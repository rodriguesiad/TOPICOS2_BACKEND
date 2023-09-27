package br.unitins.projeto.service.endereco_compra;

import br.unitins.projeto.dto.endereco_compra.EnderecoCompraDTO;
import br.unitins.projeto.dto.endereco_compra.EnderecoCompraResponseDTO;
import br.unitins.projeto.model.Endereco;
import br.unitins.projeto.model.EnderecoCompra;
import br.unitins.projeto.repository.EnderecoCompraRepository;
import br.unitins.projeto.repository.EnderecoRepository;
import br.unitins.projeto.repository.MunicipioRepository;
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
public class EnderecoCompraServiceImpl implements EnderecoCompraService {

    @Inject
    EnderecoCompraRepository repository;

    @Inject
    EnderecoRepository enderecoRepository;

    @Inject
    MunicipioRepository municipioRepository;

    @Inject
    Validator validator;

    @Override
    public List<EnderecoCompraResponseDTO> getAll() {
        List<EnderecoCompra> list = repository.listAll();
        return list.stream().map(EnderecoCompraResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public EnderecoCompraResponseDTO findById(Long id) {
        EnderecoCompra endereco = repository.findById(id);

        if (endereco == null)
            throw new NotFoundException("Endereço não encontrado.");

        return new EnderecoCompraResponseDTO(endereco);
    }

    @Override
    @Transactional
    public EnderecoCompra create(EnderecoCompraDTO enderecoCompraDTO) throws ConstraintViolationException {
        validar(enderecoCompraDTO);

        EnderecoCompra entity = new EnderecoCompra();

        entity.setLogradouro(enderecoCompraDTO.logradouro());
        entity.setBairro(enderecoCompraDTO.bairro());
        entity.setNumero(enderecoCompraDTO.numero());
        entity.setComplemento(enderecoCompraDTO.complemento());
        entity.setCep(enderecoCompraDTO.cep());
        entity.setMunicipio(municipioRepository.findById(enderecoCompraDTO.idMunicipio()));

        repository.persist(entity);

        return entity;
    }


    private void validar(EnderecoCompraDTO enderecoCompraDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<EnderecoCompraDTO>> violations = validator.validate(enderecoCompraDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }

//    @Override
//    public EnderecoCompraDTO toEndereco(Long idEndereco) {
//        Endereco endereco = enderecoRepository.findById(idEndereco);
//
//        if (endereco == null)
//            throw new NotFoundException("Endereço não encontrado.");
//
//        return new EnderecoCompraDTO(endereco.getLogradouro(),
//                endereco.getBairro(),
//                endereco.getNumero(),
//                endereco.getComplemento(),
//                endereco.getCep(),
//                endereco.getMunicipio().getId());
//    }

//    @Override
//    public EnderecoCompra toEndereco(Long idEndereco) {
//        Endereco endereco = enderecoRepository.findById(idEndereco);
//
//        if (endereco == null)
//            throw new NotFoundException("Endereço não encontrado.");
//
//        EnderecoCompra enderecoCompra = new EnderecoCompra();
//        enderecoCompra.setBairro(endereco.getBairro());
//        enderecoCompra.setCep(endereco.getCep());
//        enderecoCompra.setNumero(endereco.getNumero());
//        enderecoCompra.setComplemento(endereco.getComplemento());
//        enderecoCompra.setMunicipio(endereco.getMunicipio());
//        enderecoCompra.setLogradouro(enderecoCompra.getLogradouro());
//
//        return enderecoCompra;
//    }

    @Override
    public EnderecoCompra toModel(@Valid EnderecoCompraDTO dto) {

        EnderecoCompra enderecoCompra = new EnderecoCompra();
        enderecoCompra.setBairro(dto.bairro());
        enderecoCompra.setCep(dto.cep());
        enderecoCompra.setNumero(dto.numero());
        enderecoCompra.setComplemento(dto.complemento());
        enderecoCompra.setMunicipio(municipioRepository.findById(dto.idMunicipio()));
        enderecoCompra.setLogradouro(dto.logradouro());

        return enderecoCompra;
    }

}