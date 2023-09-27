package br.unitins.projeto.dto.endereco_compra;

import br.unitins.projeto.dto.municipio.MunicipioResponseDTO;
import br.unitins.projeto.model.EnderecoCompra;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.unitins.projeto.model.Municipio;

public record EnderecoCompraResponseDTO(
        Long id,

        String logradouro,

        String bairro,

        String numero,

        @JsonInclude(JsonInclude.Include.NON_NULL) String complemento,

        String cep,

        MunicipioResponseDTO municipio) {

    public EnderecoCompraResponseDTO(EnderecoCompra entity) {
        this(entity.getId(), entity.getLogradouro(), entity.getBairro(), entity.getNumero(),
                entity.getComplemento(), entity.getCep(), gerarMunicipioDTO(entity.getMunicipio()));
    }

    public static MunicipioResponseDTO gerarMunicipioDTO(Municipio municipio) {
        MunicipioResponseDTO municipioResponseDTO = new MunicipioResponseDTO(municipio);
        return municipioResponseDTO;
    }

}
