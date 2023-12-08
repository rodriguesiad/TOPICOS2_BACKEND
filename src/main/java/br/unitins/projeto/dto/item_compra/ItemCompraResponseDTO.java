package br.unitins.projeto.dto.item_compra;

import br.unitins.projeto.model.ItemCompra;

public record ItemCompraResponseDTO(

        Long id,

        Integer quantidade,

        Double preco,

        Long idProduto,

        String imagemProduto,

        String nome

) {

    public static ItemCompraResponseDTO valueOf(ItemCompra entity) {
        return new ItemCompraResponseDTO(entity.getId(),
                entity.getQuantidade(), entity.getPreco(),
                entity.getProduto().getId(),
                entity.getProduto().getNomeImagem(),
                entity.getProduto().getNome());
    }

}
