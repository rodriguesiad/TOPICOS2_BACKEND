package br.unitins.projeto.service.produto;

import java.util.List;

import br.unitins.projeto.dto.produto.ProdutoDTO;
import br.unitins.projeto.dto.produto.ProdutoResponseDTO;

public interface ProdutoService {
    
    List<ProdutoResponseDTO> getAll();

    ProdutoResponseDTO findById(Long id);

    ProdutoResponseDTO create(ProdutoDTO productDTO);

    ProdutoResponseDTO update(Long id, ProdutoDTO productDTO);

    void delete(Long id);

    List<ProdutoResponseDTO> findByNome(String nome, Boolean ativo, int pageNumber, int pageSize);

    ProdutoResponseDTO alterarSituacao(Long id, Boolean ativo);

    List<ProdutoResponseDTO> findAllPaginado(int pageNumber, int pageSize);

    Long countByNome(String nome, Boolean ativo);

    Long count();

}
