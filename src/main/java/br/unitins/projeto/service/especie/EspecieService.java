package br.unitins.projeto.service.especie;

import br.unitins.projeto.dto.categoria.CategoriaResponseDTO;
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

    List<EspecieResponseDTO> findByNome(String nome);

    EspecieResponseDTO alterarSituacao(Long id, AlterarSituacaoDTO dto);

    Long count();

}
