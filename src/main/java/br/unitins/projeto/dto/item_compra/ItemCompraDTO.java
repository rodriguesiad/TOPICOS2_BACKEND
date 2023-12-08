package br.unitins.projeto.dto.item_compra;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ItemCompraDTO(

        @NotNull(message = "O campo quantidade deve ser informado.")
        Integer quantidade,

        Double preco,

        @NotNull(message = "O produto deve ser informado.")
        Long idProduto

) {
}
