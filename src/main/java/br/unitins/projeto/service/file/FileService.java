package br.unitins.projeto.service.file;

import java.io.File;
import java.io.IOException;

public interface FileService {

    String salvarImagem(byte[] imagem, String nomeImagem, String caminho) throws IOException;

    File download(String nomeArquivo, String caminho);

    boolean excluirImagem(String nomeArquivo, String caminho);

}
