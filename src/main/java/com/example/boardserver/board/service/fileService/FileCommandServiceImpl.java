package com.example.boardserver.board.service.fileService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileCommandServiceImpl implements FileCommandService {

    @Value("${file.path}")
    private String filePath;

    @Value("${file.url}")
    private String fileUrl;

    @Override
    public String upLoadFile(MultipartFile file) {

        if (file.isEmpty()) return null;

        String originalFileName = file.getOriginalFilename();
        String extension = Objects.requireNonNull(originalFileName).substring(originalFileName.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString();
        String saveFileName = uuid + extension;
        String savePath = filePath + saveFileName;

        try {
            file.transferTo(new File(savePath));
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }

        return fileUrl + saveFileName;
    }

    @Override
    public Resource getImage(String filename) {

        Resource resource = null;

        try {
            resource = new UrlResource("file:" + filePath + filename);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }

        return resource ;
    }
}
