package it.uniroma3.siw.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {
	
    public String saveFile(MultipartFile file, String uploadDirPath) throws IOException {
        Path uploadDir = Paths.get(uploadDirPath);
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path path = uploadDir.resolve(fileName);
        Files.write(path, file.getBytes());

        // Rimuove la prima directory dal percorso
        String relativePath = uploadDir.subpath(1, uploadDir.getNameCount()).resolve(fileName).toString();
        return "/" + relativePath;
    }

    public void deleteFile(String fileUrl, String uploadDirPath) throws IOException {
        Path path = Paths.get(uploadDirPath).resolve(Paths.get(fileUrl).getFileName().toString());
        if (Files.exists(path)) {
            Files.delete(path);
        }
    }
}
