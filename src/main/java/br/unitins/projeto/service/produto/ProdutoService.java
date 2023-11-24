package br.unitins.projeto.service.produto;

import java.io.IOException;
import java.util.List;

import br.unitins.projeto.dto.produto.ProdutoDTO;
import br.unitins.projeto.dto.produto.ProdutoResponseDTO;
import br.unitins.projeto.form.ProdutoImageForm;
import jakarta.validation.Valid;

public interface ProdutoService {
    
    List<ProdutoResponseDTO> getAll();

    ProdutoResponseDTO findById(Long id);

    ProdutoResponseDTO create(@Valid ProdutoDTO productDTO);

    ProdutoResponseDTO update(Long id, @Valid ProdutoDTO productDTO);

    void delete(Long id);

    List<ProdutoResponseDTO> findByNome(String nome, Boolean ativo, int pageNumber, int pageSize);

    ProdutoResponseDTO alterarSituacao(Long id, Boolean ativo);

    List<ProdutoResponseDTO> findAllPaginado(int pageNumber, int pageSize);

    Long countByNome(String nome, Boolean ativo);

    Long count();

    void salvarImagens(ProdutoImageForm imagens) throws IOException;

}
