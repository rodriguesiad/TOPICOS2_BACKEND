package br.unitins.projeto.dto.metodo.recebimento.boleto;

import br.unitins.projeto.model.BoletoRecebimento;

public record BoletoRecebimentoResponseDTO(

        Long id,
        String banco,

        String nome,

        String cnpj,

        String agencia,

        String conta

) {
    public BoletoRecebimentoResponseDTO(BoletoRecebimento entity) {
        this(entity.getId(), entity.getBanco(), entity.getNome(), entity.getCnpj(), entity.getAgencia(), entity.getConta());
    }

}