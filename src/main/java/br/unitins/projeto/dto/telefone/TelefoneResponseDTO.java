package br.unitins.projeto.dto.telefone;

import br.unitins.projeto.model.Telefone;

public record TelefoneResponseDTO(
        Long id,
        String codigoArea,
        String numero) {

    public TelefoneResponseDTO(Telefone entity) {
        this(entity.getId(), entity.getCodigoArea(), entity.getNumero());
    }

}
