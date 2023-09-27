package br.unitins.projeto.dto.compra;

import br.unitins.projeto.dto.endereco_compra.EnderecoCompraDTO;
import br.unitins.projeto.dto.item_compra.ItemCompraDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record CompraDTO(

        @Valid
        @NotNull(message = "O endereço da compra dever ser informado.")
        EnderecoCompraDTO enderecoCompra,

        @Valid
        @NotNull(message = "Os itens da compra devem ser informados.")
        List<ItemCompraDTO> itensCompra

) {
}
