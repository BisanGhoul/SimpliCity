package com.jhf.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ProfilePicturesService {
    private static final Path PICTURES_STORE = Paths.get("images/profile");

    public String store(MultipartFile file) throws IOException {
        String uniqueFileName = UUID.randomUUID().toString();

        String originalFileName = file.getOriginalFilename();
        if (originalFileName != null) {
            int index = originalFileName.lastIndexOf('.');
            if (index > 0) {
                uniqueFileName = uniqueFileName + '.' + originalFileName.substring(index + 1);
            }
        }

        Path uniqueFilePath = PICTURES_STORE.resolve(uniqueFileName);
        Files.copy(file.getInputStream(), uniqueFilePath);
        return PICTURES_STORE.resolve(uniqueFileName).toString().replace("\\", "/");
    }
}