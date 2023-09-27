package br.unitins.projeto.dto.municipio;

import br.unitins.projeto.dto.estado.EstadoResponseDTO;
import br.unitins.projeto.model.Estado;
import br.unitins.projeto.model.Municipio;

public record MunicipioResponseDTO(
        Long id,
        String descricao,
        EstadoResponseDTO estado) {

    public MunicipioResponseDTO(Municipio entity) {
        this(entity.getId(), entity.getDescricao(), gerarEstadoDTO(entity.getEstado()));
    }

    public static EstadoResponseDTO gerarEstadoDTO(Estado estado) {
        EstadoResponseDTO estadoResponseDTO = new EstadoResponseDTO(estado);
        return estadoResponseDTO;
    }

}
