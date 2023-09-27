package br.unitins.projeto.service.file;

import jakarta.enterprise.context.ApplicationScoped;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class FileServiceImpl implements FileService {
    private final String PATH = System.getProperty("user.home")
            + File.separator + "quarkus"
            + File.separator + "images"
            + File.separator;

    @Override
    public String salvarImagem(byte[] imagem, String nomeImagem, String caminho, String identificador) throws IOException {
        String mimeType = Files.probeContentType(new File(nomeImagem).toPath());
        List<String> listMimeType = Arrays.asList("image/jpg", "image/png", "image/gif");

        if (!listMimeType.contains(mimeType)) {
            throw new IOException("Tipo de imagem não suportada.");
        }

        if (imagem.length > (1024 * 1024 * 10)) {
            throw new IOException("Arquivo muito grande.");
        }

        File diretorio = new File(PATH + caminho + File.separator + identificador + File.separator);

        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }

        String nomeArquivo = UUID.randomUUID()
                + "." + mimeType.substring(mimeType.lastIndexOf("/") + 1);

        String path = PATH + caminho + File.separator + identificador + File.separator + nomeArquivo;

        File file = new File(path);

        while (file.exists()) {
            path += this.generateRandomString();
            file = new File(path);
        }

        file.createNewFile();

        FileOutputStream fos = new FileOutputStream(file);
        fos.write(imagem);
        fos.flush();
        fos.close();

        return nomeArquivo;
    }

    @Override
    public File download(String nomeArquivo, String caminho, String identificador) {
        File file = new File(PATH + caminho + File.separator + identificador + File.separator + nomeArquivo);
        return file;
    }

    private String generateRandomString() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        Random random = new Random();
        String randomString = random.ints(3, 0, characters.length())
                .mapToObj(characters::charAt)
                .map(Object::toString)
                .collect(Collectors.joining());

        return randomString;
    }

}
