package br.unitins.projeto.dto.compra;

import br.unitins.projeto.dto.endereco_compra.EnderecoCompraResponseDTO;
import br.unitins.projeto.dto.item_compra.ItemCompraResponseDTO;
import br.unitins.projeto.model.Compra;
import br.unitins.projeto.model.ItemCompra;
import br.unitins.projeto.model.StatusCompra;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record CompraResponseDTO(

        Long id,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDateTime data,

        Double totalCompra,

        EnderecoCompraResponseDTO enderecoCompra,

        StatusCompra statusCompra,

        List<ItemCompraResponseDTO> itensCompra

) {

    public static CompraResponseDTO valueOf(Compra entity) {
        return new CompraResponseDTO(entity.getId(),
                entity.getDataPagamento(),
                entity.getTotalCompra(),
                new EnderecoCompraResponseDTO(entity.getEnderecoCompra()),
                entity.getStatusCompra(),
                gerarItemCompraResponseDTO(entity.getItensCompra()));
    }

    private static List<ItemCompraResponseDTO> gerarItemCompraResponseDTO(List<ItemCompra> list) {
        if (list != null)
            return list.stream().map(item -> ItemCompraResponseDTO.valueOf(item)).collect(Collectors.toList());
        return null;
    }

}
