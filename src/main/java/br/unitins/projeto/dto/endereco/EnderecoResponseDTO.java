package br.unitins.projeto.dto.endereco;

import br.unitins.projeto.dto.municipio.MunicipioResponseDTO;
import br.unitins.projeto.model.Endereco;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.unitins.projeto.model.Municipio;

public record EnderecoResponseDTO(
        Long id,

        Boolean principal,

        String logradouro,

        String bairro,

        String numero,

        @JsonInclude(JsonInclude.Include.NON_NULL) String complemento,

        String cep,

        @JsonInclude(JsonInclude.Include.NON_NULL) String titulo,

        MunicipioResponseDTO municipio) {

    public EnderecoResponseDTO(Endereco entity) {
        this(entity.getId(), entity.getPrincipal(), entity.getLogradouro(),
                entity.getBairro(), entity.getNumero(), entity.getComplemento(), entity.getCep(), entity.getTitulo(),
                gerarMunicipioDTO(entity.getMunicipio()));
    }

    public static MunicipioResponseDTO gerarMunicipioDTO(Municipio municipio) {
        MunicipioResponseDTO municipioResponseDTO = new MunicipioResponseDTO(municipio);
        return municipioResponseDTO;
    }

}
