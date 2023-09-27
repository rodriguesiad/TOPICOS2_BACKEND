package br.unitins.projeto.service.file;

import java.io.File;
import java.io.IOException;

public interface FileService {

    String salvarImagem(byte[] imagem, String nomeImagem, String caminho, String identificador) throws IOException;

    File download(String nomeArquivo, String caminho, String identificador);

}
