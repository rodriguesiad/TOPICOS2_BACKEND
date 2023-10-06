package br.unitins.projeto.service.produto;

import java.util.List;

import br.unitins.projeto.dto.produto.ProdutoDTO;
import br.unitins.projeto.dto.produto.ProdutoResponseDTO;
import br.unitins.projeto.dto.situacao.AlterarSituacaoDTO;

public interface ProdutoService {
    
    List<ProdutoResponseDTO> getAll();

    ProdutoResponseDTO findById(Long id);

    ProdutoResponseDTO create(ProdutoDTO productDTO);

    ProdutoResponseDTO update(Long id, ProdutoDTO productDTO);

    void delete(Long id);

    List<ProdutoResponseDTO> findByNome(String nome);

    ProdutoResponseDTO alterarSituacao(Long id, AlterarSituacaoDTO dto);

    List<ProdutoResponseDTO> findAllPaginado(int pageNumber, int pageSize);

    Long count();

}
