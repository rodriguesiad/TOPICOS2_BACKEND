package br.unitins.projeto.dto.estado;

import br.unitins.projeto.model.Estado;

public record EstadoResponseDTO(
        Long id,
        String sigla,
        String descricao) {

    public EstadoResponseDTO(Estado entity) {
        this(entity.getId(), entity.getSigla(), entity.getDescricao());
    }

}
