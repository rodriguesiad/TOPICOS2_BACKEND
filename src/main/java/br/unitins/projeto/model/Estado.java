package br.unitins.projeto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Estado extends DefaultEntity {

    @Column(nullable = false, length = 2)
    private String sigla;

    @Column(nullable = false, length = 60)
    private String descricao;

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
