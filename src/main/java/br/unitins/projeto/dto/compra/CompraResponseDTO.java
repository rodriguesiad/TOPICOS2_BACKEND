package br.unitins.projeto.dto.compra;

import br.unitins.projeto.dto.endereco_compra.EnderecoCompraResponseDTO;
import br.unitins.projeto.dto.item_compra.ItemCompraResponseDTO;
import br.unitins.projeto.dto.metodo.pagamento.boleto.BoletoResponseDTO;
import br.unitins.projeto.dto.usuario.cadastro.CadastroAdminDTO;
import br.unitins.projeto.dto.usuario.cadastro.CadastroAdminResponseDTO;
import br.unitins.projeto.model.Compra;
import br.unitins.projeto.model.ItemCompra;
import br.unitins.projeto.model.StatusCompra;
import br.unitins.projeto.model.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record CompraResponseDTO(

        Long id,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDateTime data,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDateTime dataPagameto,

        CadastroAdminResponseDTO usuario,

        Double totalCompra,

        EnderecoCompraResponseDTO enderecoCompra,

        StatusCompra statusCompra,

        List<ItemCompraResponseDTO> itensCompra,

        Boolean sinBoleto,
        Boolean sinPix

) {

    public static CompraResponseDTO valueOf(Compra entity) {
        return new CompraResponseDTO(entity.getId(),
                entity.getDataInclusao(),
                entity.getDataPagamento(),
                CadastroAdminResponseDTO.valueOf(entity.getUsuario()),
                entity.getTotalCompra(),
                new EnderecoCompraResponseDTO(entity.getEnderecoCompra()),
                entity.getStatusCompra(),
                gerarItemCompraResponseDTO(entity.getItensCompra()),
                entity.getSinBoleto(),
                entity.getSinPix());
    }

    private static List<ItemCompraResponseDTO> gerarItemCompraResponseDTO(List<ItemCompra> list) {
        if (list != null)
            return list.stream().map(item -> ItemCompraResponseDTO.valueOf(item)).collect(Collectors.toList());
        return null;
    }

}
