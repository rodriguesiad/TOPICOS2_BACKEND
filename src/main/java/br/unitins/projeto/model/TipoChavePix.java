package br.unitins.projeto.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TipoChavePix {
    ALEATORIA(1, "Aleatória"),
    CPF_CNPJ(2, "CPF/CNPJ"),
    EMAIL(3, "E-mail"),
    CELULAR(4, "Celular");

    private int id;
    private String label;

    TipoChavePix(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static TipoChavePix valueOf(Integer id) throws IllegalArgumentException {
        if (id == null)
            return null;
        for (TipoChavePix perfil : TipoChavePix.values()) {
            if (id.equals(perfil.getId()))
                return perfil;
        }
        throw new IllegalArgumentException("Id inválido:" + id);
    }

}
