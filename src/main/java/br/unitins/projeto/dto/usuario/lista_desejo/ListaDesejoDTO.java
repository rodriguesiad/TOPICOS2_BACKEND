package br.unitins.projeto.dto.usuario.lista_desejo;

import jakarta.validation.constraints.NotNull;

public record ListaDesejoDTO(

        @NotNull(message = "O produto deve ser informado.")
        Long idProduto

) {
}