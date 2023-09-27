package br.unitins.projeto.service.telefone;

import br.unitins.projeto.dto.telefone.TelefoneDTO;
import br.unitins.projeto.dto.telefone.TelefoneResponseDTO;
import br.unitins.projeto.model.Telefone;

import jakarta.validation.Valid;
import java.util.List;

public interface TelefoneService {

    List<TelefoneResponseDTO> getAll();

    TelefoneResponseDTO findById(Long id);

    TelefoneResponseDTO create(TelefoneDTO productDTO);

    TelefoneResponseDTO update(Long id, TelefoneDTO productDTO);

    Telefone toModel(@Valid TelefoneDTO telefoneDTO);

    void delete(Long id);

    Long count();

}
