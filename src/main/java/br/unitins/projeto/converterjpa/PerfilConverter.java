package br.unitins.projeto.converterjpa;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import br.unitins.projeto.model.Perfil;

@Converter(autoApply = true)
public class PerfilConverter implements AttributeConverter<Perfil, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Perfil perfil) {
        return perfil == null ? null : perfil.getId();
    }

    @Override
    public Perfil convertToEntityAttribute(Integer id) {
        return id == null ? null : Perfil.valueOf(id);
    }

}
