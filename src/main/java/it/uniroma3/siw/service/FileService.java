package it.uniroma3.siw.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {

    public String uploadFile(MultipartFile file, String uploadDir) throws IOException {
        // Assicurati che la directory di upload esista
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Salva il file nel server
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path path = uploadPath.resolve(fileName);
        Files.write(path, file.getBytes());

        // Ritorna il percorso relativo del file
        return uploadDir + "/" + fileName;
    }

    public void deleteFile(String fileUrl, String uploadDir) throws IOException {
        Path path = Paths.get(uploadDir).resolve(Paths.get(fileUrl).getFileName().toString());
        if (Files.exists(path)) {
            Files.delete(path);
        }
    }
}