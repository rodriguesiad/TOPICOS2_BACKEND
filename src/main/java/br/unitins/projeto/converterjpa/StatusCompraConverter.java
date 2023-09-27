package br.unitins.projeto.converterjpa;

import br.unitins.projeto.model.StatusCompra;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusCompraConverter implements AttributeConverter<StatusCompra, Integer> {

    @Override
    public Integer convertToDatabaseColumn(StatusCompra statusCompra) {
        return statusCompra == null ? null : statusCompra.getId();
    }

    @Override
    public StatusCompra convertToEntityAttribute(Integer id) {
        return StatusCompra.valueOf(id);
    }

}
