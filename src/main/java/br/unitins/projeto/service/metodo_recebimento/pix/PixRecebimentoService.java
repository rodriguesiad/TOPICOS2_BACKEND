package br.unitins.projeto.service.metodo_recebimento.pix;

import br.unitins.projeto.dto.metodo.recebimento.pix.PixRecebimentoDTO;
import br.unitins.projeto.dto.metodo.recebimento.pix.PixRecebimentoResponseDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface PixRecebimentoService {

    List<PixRecebimentoResponseDTO> findAllInativo(int page, int pageSize);

    PixRecebimentoResponseDTO findByAtivo();

    List<PixRecebimentoResponseDTO> findByChave(String chave, int page, int pageSize);

    PixRecebimentoResponseDTO findById(Long id);

    PixRecebimentoResponseDTO create(@Valid PixRecebimentoDTO dto);

    PixRecebimentoResponseDTO update(Long id, @Valid PixRecebimentoDTO dto);

    void delete(Long id);

    long count();

    long countByChave(String chave);

}
