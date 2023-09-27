package br.unitins.projeto.service.item_compra;

import br.unitins.projeto.dto.item_compra.ItemCompraDTO;
import br.unitins.projeto.dto.item_compra.ItemCompraResponseDTO;
import br.unitins.projeto.model.ItemCompra;
import jakarta.validation.Valid;

import java.util.List;

public interface ItemCompraService {

    List<ItemCompraResponseDTO> getAll();

    ItemCompraResponseDTO findById(Long id);

    ItemCompraResponseDTO create(ItemCompra entity);

    void delete(Long id);

    ItemCompra toModel(@Valid ItemCompraDTO dto);

}
