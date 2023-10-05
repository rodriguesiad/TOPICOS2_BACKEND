package br.unitins.projeto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import br.unitins.projeto.dto.telefone.TelefoneResponseDTO;

import java.util.Objects;

@Entity
public class Telefone extends DefaultEntity {

    @Column(nullable = false, length = 2)
    private String codigoArea;

    @Column(nullable = false, length = 9)
    private String numero;

    public String getCodigoArea() {
        return codigoArea;
    }

    public void setCodigoArea(String codigoArea) {
        this.codigoArea = codigoArea;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Telefone() {
    }

    public Telefone(TelefoneResponseDTO dto) {
        super.setId(dto.id());
        this.codigoArea = dto.codigoArea();
        this.numero = dto.numero();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Telefone telefone = (Telefone) o;
        return Objects.equals(codigoArea, telefone.codigoArea) && Objects.equals(numero, telefone.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigoArea, numero);
    }

}
