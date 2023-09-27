package br.unitins.projeto.dto.situacao;

import jakarta.validation.constraints.NotNull;

public record AlterarSituacaoDTO(
        @NotNull
        Boolean situacao
) {
}
