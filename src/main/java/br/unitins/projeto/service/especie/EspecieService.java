package br.unitins.projeto.service.especie;

import br.unitins.projeto.dto.especie.EspecieDTO;
import br.unitins.projeto.dto.especie.EspecieResponseDTO;
import br.unitins.projeto.dto.situacao.AlterarSituacaoDTO;

import java.util.List;

public interface EspecieService {

    List<EspecieResponseDTO> getAll();

    EspecieResponseDTO findById(Long id);

    EspecieResponseDTO create(EspecieDTO productDTO);

    EspecieResponseDTO update(Long id, EspecieDTO productDTO);

    void delete(Long id);

    EspecieResponseDTO alterarSituacao(Long id, AlterarSituacaoDTO dto);

    List<EspecieResponseDTO> findAllPaginado(int pageNumber, int pageSize);

    Long countByNome(String nome, Boolean ativo);

    List<EspecieResponseDTO> findByNome(String nome, Boolean ativo, int pageNumber, int pageSize);

    Long count();

}
