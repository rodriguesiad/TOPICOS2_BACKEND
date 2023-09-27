package br.unitins.projeto.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StatusCompra {
    PROCESSANDO(1, "Processando"),
    PAGA(2, "Paga"),
    CANCELADA(3, "Cancelada"),
    ENVIADA(4, "Enviada"),
    FINALIZADA(5, "Finalizada");

    private int id;
    private String label;

    StatusCompra(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static StatusCompra valueOf(Integer id) throws IllegalArgumentException {
        if (id == null)
            return null;
        for (StatusCompra perfil : StatusCompra.values()) {
            if (id.equals(perfil.getId()))
                return perfil;
        }
        throw new IllegalArgumentException("Id inválido:" + id);
    }

}
