package br.unitins.projeto.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.ws.rs.FormParam;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

public class ImageForm {

    @FormParam("nomeImagem")
    @NotBlank(message = "O nome da imagem deve ser informada.")
    private String nomeImagem;

    @FormParam("imagem")
    @PartType("application/octet-stream")
    @NotBlank(message = "A imagem deve ser anexada.")
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
    
}
