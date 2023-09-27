package br.unitins.projeto.dto.item_compra;

import br.unitins.projeto.dto.produto.ProdutoResponseDTO;
import br.unitins.projeto.model.ItemCompra;

public record ItemCompraResponseDTO(

        Long id,

        Integer quantidade,

        Double preco,

        ProdutoResponseDTO produto

) {
    
    public static ItemCompraResponseDTO valueOf(ItemCompra entity) {
        return new ItemCompraResponseDTO(entity.getId(),
                entity.getQuantidade(), entity.getPreco(),
                new ProdutoResponseDTO(entity.getProduto()));
    }

}
