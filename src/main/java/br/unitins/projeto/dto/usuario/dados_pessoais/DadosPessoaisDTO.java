package br.unitins.projeto.dto.usuario.dados_pessoais;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.smallrye.common.constraint.NotNull;

import java.time.LocalDate;

public record DadosPessoaisDTO(

        @NotNull() @JsonFormat(pattern = "yyyy-MM-dd") LocalDate dataNascimento) {

}
