package br.unitins.projeto.service.raca;

import br.unitins.projeto.dto.raca.RacaDTO;
import br.unitins.projeto.dto.raca.RacaResponseDTO;
import br.unitins.projeto.dto.situacao.AlterarSituacaoDTO;

import java.util.List;

public interface RacaService {

    List<RacaResponseDTO> getAll();

    RacaResponseDTO findById(Long id);

    RacaResponseDTO create(RacaDTO productDTO);

    RacaResponseDTO update(Long id, RacaDTO productDTO);

    void delete(Long id);

    List<RacaResponseDTO> findByNome(String nome);

    RacaResponseDTO alterarSituacao(Long id, AlterarSituacaoDTO dto);

    Long count();

}
