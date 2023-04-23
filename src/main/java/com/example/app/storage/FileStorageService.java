package com.example.app.storage;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {
    private final String fileStorageLocation;
    private final String imageStorageLocation;

    public FileStorageService(@Value("${app.storage.location}") String fileStorageLocation) throws FileNotFoundException {
        this.fileStorageLocation = fileStorageLocation;
        this.imageStorageLocation = fileStorageLocation + "/img/";

        Path fileStoragePath = Path.of(fileStorageLocation);
        checkDirectoryExists(fileStoragePath);
        Path imageStoragePath = Path.of(imageStorageLocation);
        checkDirectoryExists(imageStoragePath);
    }

    public String saveImage(MultipartFile file) {
        return saveFile(file, imageStorageLocation);
    }

    public String saveFile(MultipartFile file) {
        return saveFile(file, fileStorageLocation);
    }

    public void deleteImage(String fileName) {
        File file = new File(imageStorageLocation + fileName);
        file.delete();
    }


    private void checkDirectoryExists(Path path) throws FileNotFoundException {
        if (Files.notExists(path)) {
            throw new FileNotFoundException("Directory %s does not exist.".formatted(path.toString()));
        }
    }

    private String saveFile(MultipartFile file, String storageLocation) {
        Path filePath = createFilePath(file, storageLocation);
        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return filePath.getFileName().toString();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private Path createFilePath(MultipartFile file, String storageLocation) {
        String originalFilename = file.getOriginalFilename();
        String fileBaseName = FilenameUtils.getBaseName(originalFilename);
        String fileExtension = FilenameUtils.getExtension(originalFilename);

        String completeFilename;
        Path filePath;
        int fileIndex = 0;

        do {
            completeFilename = fileBaseName + fileIndex + "." + fileExtension;
            filePath = Paths.get(storageLocation, completeFilename);
            fileIndex++;
        } while (Files.exists(filePath));
        return filePath;
    }
}
