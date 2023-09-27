package br.unitins.projeto.converterjpa;

import br.unitins.projeto.model.TipoChavePix;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TipoChavePixConverter implements AttributeConverter<TipoChavePix, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoChavePix tipoChavePix) {
        return tipoChavePix == null ? null : tipoChavePix.getId();
    }

    @Override
    public TipoChavePix convertToEntityAttribute(Integer id) {
        return TipoChavePix.valueOf(id);
    }

}
