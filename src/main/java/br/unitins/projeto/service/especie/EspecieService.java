package br.unitins.projeto.service.especie;

import br.unitins.projeto.dto.especie.EspecieDTO;
import br.unitins.projeto.dto.especie.EspecieResponseDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface EspecieService {

    List<EspecieResponseDTO> getAll();

    EspecieResponseDTO findById(Long id);

    EspecieResponseDTO create(@Valid EspecieDTO productDTO);

    EspecieResponseDTO update(Long id, @Valid EspecieDTO productDTO);

    void delete(Long id);

    EspecieResponseDTO alterarSituacao(Long id, Boolean situacao);

    List<EspecieResponseDTO> findAllPaginado(int pageNumber, int pageSize);

    Long countByNome(String nome, Boolean ativo);

    List<EspecieResponseDTO> findByNome(String nome, Boolean ativo, int pageNumber, int pageSize);

    Long count();

}
