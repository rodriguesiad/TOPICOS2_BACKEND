package br.unitins.projeto.service.categoria;

import br.unitins.projeto.dto.categoria.CategoriaDTO;
import br.unitins.projeto.dto.categoria.CategoriaResponseDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface CategoriaService {

    List<CategoriaResponseDTO> getAll();

    CategoriaResponseDTO findById(Long id);

    CategoriaResponseDTO create(@Valid CategoriaDTO productDTO);

    CategoriaResponseDTO update(Long id, @Valid CategoriaDTO productDTO);

    void delete(Long id);

    List<CategoriaResponseDTO> findByCampoBusca(String nome, String situacao, int pageNumber, int pageSize);

    CategoriaResponseDTO alterarSituacao(Long id, Boolean situacao);

    Long count();

    List<CategoriaResponseDTO> findAllPaginado(int pageNumber, int pageSize);

    Long countByCampoBusca(String nome, String situacao);

}
