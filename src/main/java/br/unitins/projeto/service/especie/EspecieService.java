package br.unitins.projeto.service.especie;

import br.unitins.projeto.dto.especie.EspecieDTO;
import br.unitins.projeto.dto.especie.EspecieResponseDTO;

import java.util.List;

public interface EspecieService {

    List<EspecieResponseDTO> getAll();

    EspecieResponseDTO findById(Long id);

    EspecieResponseDTO create(EspecieDTO productDTO);

    EspecieResponseDTO update(Long id, EspecieDTO productDTO);

    void delete(Long id);

    EspecieResponseDTO alterarSituacao(Long id, Boolean situacao);

    List<EspecieResponseDTO> findAllPaginado(int pageNumber, int pageSize);

    Long countByNome(String nome, String situacao);

    List<EspecieResponseDTO> findByNome(String nome, String situacao, int pageNumber, int pageSize);

    Long count();

}
