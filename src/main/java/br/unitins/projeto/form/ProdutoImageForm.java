package br.unitins.projeto.form;

import jakarta.ws.rs.FormParam;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

public class ProdutoImageForm {

    @FormParam("id")
    private Long id;

    @FormParam("nomeImagem")
    private String nomeImagem;

    @FormParam("imagem")
    @PartType("application/octet-stream")
    private byte[] imagem;

    public String getNomeImagem() {
        return nomeImagem;
    }

    public void setNomeImagem(String nomeImagem) {
        this.nomeImagem = nomeImagem;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
