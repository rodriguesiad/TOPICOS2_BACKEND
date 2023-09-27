package br.unitins.projeto.service.endereco_compra;

import java.util.List;

import br.unitins.projeto.dto.endereco_compra.EnderecoCompraDTO;
import br.unitins.projeto.dto.endereco_compra.EnderecoCompraResponseDTO;
import br.unitins.projeto.model.EnderecoCompra;
import jakarta.validation.Valid;

public interface EnderecoCompraService {

    List<EnderecoCompraResponseDTO> getAll();

    EnderecoCompraResponseDTO findById(Long id);

    EnderecoCompra create(EnderecoCompraDTO productDTO);
//
//    EnderecoCompra toEndereco(Long idEndereco);

    EnderecoCompra toModel(@Valid EnderecoCompraDTO dto);

}