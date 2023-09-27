package br.unitins.projeto.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PorteAnimal {
    PEQUENO(1, "Pequeno"),
    MEDIO(2, "Médio"),
    GRANDE(3, "Grande");

    private int id;
    private String label;

    PorteAnimal(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static PorteAnimal valueOf(Integer id) throws IllegalArgumentException {
        if (id == null)
            return null;
        for (PorteAnimal perfil : PorteAnimal.values()) {
            if (id.equals(perfil.getId()))
                return perfil;
        }
        throw new IllegalArgumentException("Id inválido:" + id);
    }

}
