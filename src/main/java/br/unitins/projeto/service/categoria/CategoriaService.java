package br.unitins.projeto.service.categoria;

import br.unitins.projeto.dto.categoria.CategoriaDTO;
import br.unitins.projeto.dto.categoria.CategoriaResponseDTO;

import java.util.List;

public interface CategoriaService {

    List<CategoriaResponseDTO> getAll();

    CategoriaResponseDTO findById(Long id);

    CategoriaResponseDTO create(CategoriaDTO productDTO);

    CategoriaResponseDTO update(Long id, CategoriaDTO productDTO);

    void delete(Long id);

    List<CategoriaResponseDTO> findByCampoBusca(String nome, Boolean ativo, int pageNumber, int pageSize);

    CategoriaResponseDTO alterarSituacao(Long id, Boolean situacao);

    Long count();

    List<CategoriaResponseDTO> findAllPaginado(int pageNumber, int pageSize);

    Long countByCampoBusca(String nome, Boolean ativo);

}
