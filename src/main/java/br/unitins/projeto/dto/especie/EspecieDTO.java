package br.unitins.projeto.dto.especie;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EspecieDTO(
        @NotBlank(message = "O campo nome deve ser informado.")
        @Size(max = 100, message = "O campo nome deve possuir no máximo 100 caracteres.")
        String nome

) {

}
