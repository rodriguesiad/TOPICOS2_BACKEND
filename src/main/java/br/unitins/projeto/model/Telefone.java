package br.unitins.projeto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import br.unitins.projeto.dto.telefone.TelefoneResponseDTO;

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
}
