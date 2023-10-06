package br.unitins.projeto.dto.municipio;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MunicipioDTO(

        @NotBlank(message = "O campo nome deve ser informado.")
        @Size(max = 60, message = "O campo nome deve possuir no máximo 60 caracteres.")
        String nome,

        @NotNull(message = "O estado deve ser informado.") 
        Long idEstado

) {

}
