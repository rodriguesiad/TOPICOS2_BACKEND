package br.unitins.projeto.service.metodo_recebimento.boleto;

import br.unitins.projeto.dto.metodo.recebimento.boleto.BoletoRecebimentoDTO;
import br.unitins.projeto.dto.metodo.recebimento.boleto.BoletoRecebimentoResponseDTO;

import java.util.List;

public interface BoletoRecebimentoService {

    List<BoletoRecebimentoResponseDTO> listAllInativo(int page, int pageSize);

    BoletoRecebimentoResponseDTO findByAtivo();

    List<BoletoRecebimentoResponseDTO> findByCNPJ(String cnpj, int page, int pageSize);

    BoletoRecebimentoResponseDTO findById(Long id);

    BoletoRecebimentoResponseDTO create(BoletoRecebimentoDTO dto);

    BoletoRecebimentoResponseDTO update(Long id, BoletoRecebimentoDTO dto);

    void delete(Long id);

    long count();

    long countByCNPJ(String cnpj);
}
