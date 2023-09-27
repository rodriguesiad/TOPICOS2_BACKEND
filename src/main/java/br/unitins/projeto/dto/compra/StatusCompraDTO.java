package br.unitins.projeto.dto.compra;

import jakarta.validation.constraints.NotNull;

public record StatusCompraDTO(

        @NotNull(message = "O novo status da compra deve ser informado.")
        Integer statusCompra

) {
}
