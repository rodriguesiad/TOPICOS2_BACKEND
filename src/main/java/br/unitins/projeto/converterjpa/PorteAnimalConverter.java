package br.unitins.projeto.converterjpa;

import br.unitins.projeto.model.PorteAnimal;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PorteAnimalConverter implements AttributeConverter<PorteAnimal, Integer> {

    @Override
    public Integer convertToDatabaseColumn(PorteAnimal porteAnimal) {
        return porteAnimal == null ? null : porteAnimal.getId();
    }

    @Override
    public PorteAnimal convertToEntityAttribute(Integer id) {
        return PorteAnimal.valueOf(id);
    }

}
