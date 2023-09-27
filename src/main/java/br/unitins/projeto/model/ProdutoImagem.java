package br.unitins.projeto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ProdutoImagem extends DefaultEntity {

    @Column(name = "nome_arquivo", nullable = false, unique = true)
    private String nomeArquivo;

    @ManyToOne
    @JoinColumn(name = "id_produtp", nullable = false)
    private Produto produto;

    public ProdutoImagem(String nomeArquivo, Produto produto) {
        this.nomeArquivo = nomeArquivo;
        this.produto = produto;
    }

    public ProdutoImagem() {
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

}