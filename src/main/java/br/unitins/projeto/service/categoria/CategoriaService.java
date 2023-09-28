package br.unitins.projeto.service.categoria;

import br.unitins.projeto.dto.categoria.CategoriaDTO;
import br.unitins.projeto.dto.categoria.CategoriaResponseDTO;
import br.unitins.projeto.dto.situacao.AlterarSituacaoDTO;

import java.util.List;

public interface CategoriaService {

    List<CategoriaResponseDTO> getAll();

    CategoriaResponseDTO findById(Long id);

    CategoriaResponseDTO create(CategoriaDTO productDTO);

    CategoriaResponseDTO update(Long id, CategoriaDTO productDTO);

    void delete(Long id);

    List<CategoriaResponseDTO> findByNome(String nome);

    CategoriaResponseDTO alterarSituacao(Long id, AlterarSituacaoDTO dto);

    Long count();

    List<CategoriaResponseDTO> findAllPaginado(int pageNumber, int pageSize);

}
