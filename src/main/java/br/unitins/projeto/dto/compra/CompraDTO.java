package br.unitins.projeto.dto.compra;

import java.util.List;

import br.unitins.projeto.dto.endereco_compra.EnderecoCompraDTO;
import br.unitins.projeto.dto.item_compra.ItemCompraDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record CompraDTO(

        @Valid
        @NotNull(message = "O endereço da compra dever ser informado.")
        EnderecoCompraDTO enderecoCompra,

        @Valid
        @NotNull(message = "Os itens da compra devem ser informados.")
        List<ItemCompraDTO> itensCompra

) {
}
