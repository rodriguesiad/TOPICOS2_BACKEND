package br.unitins.projeto.dto.telefone;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TelefoneDTO(

    @NotBlank(message = "O campo código da área deve ser informado.")
    @Size(min = 2 ,max = 2, message = "O código da área deve posssuir 2 caracteres.")
    String codigoArea,

    @NotBlank(message = "O campo número deve ser informado.")
    @Size(min = 9, max = 9, message = "O campo número deve possuir 9 caracteres.")
    String numero

) {
  
}
