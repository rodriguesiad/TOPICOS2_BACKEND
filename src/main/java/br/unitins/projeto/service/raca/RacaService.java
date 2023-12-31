package br.unitins.projeto.service.raca;

import br.unitins.projeto.dto.raca.RacaDTO;
import br.unitins.projeto.dto.raca.RacaResponseDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface RacaService {

    List<RacaResponseDTO> getAll();

    RacaResponseDTO findById(Long id);

    RacaResponseDTO create(@Valid RacaDTO productDTO);

    RacaResponseDTO update(Long id, @Valid RacaDTO productDTO);

    void delete(Long id);

    List<RacaResponseDTO> findByNome(String nome, String situacao, int pageNumber, int pageSize);

    RacaResponseDTO alterarSituacao(Long id, Boolean situacao);

    Long count();

    List<RacaResponseDTO> findAllPaginado(int pageNumber, int pageSize);

    Long countByNome(String nome, String situacao);

}
