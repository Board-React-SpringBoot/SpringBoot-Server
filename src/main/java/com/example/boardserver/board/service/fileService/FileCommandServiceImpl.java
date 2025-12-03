package com.example.boardserver.board.service.fileService;

import com.example.boardserver.common.code.status.ErrorStatus;
import com.example.boardserver.exception.handler.FileHandler;
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

        if (file.isEmpty()) throw new FileHandler(ErrorStatus.FILE_IS_EMPTY);

        String originalFileName = file.getOriginalFilename();
        String extension = Objects.requireNonNull(originalFileName).substring(originalFileName.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString();
        String saveFileName = uuid + extension;
        String savePath = filePath + saveFileName;

        try {
            file.transferTo(new File(savePath));
        } catch (Exception exception) {
            throw new FileHandler(ErrorStatus.FILE_UPLOAD_ERROR);
        }

        return fileUrl + saveFileName;
    }

    @Override
    public Resource getImage(String filename) {

        Resource resource = null;

        try {
            resource = new UrlResource("file:" + filePath + filename);

            if (!resource.exists() || !resource.isReadable()) {
                throw new FileHandler(ErrorStatus.FILE_NOT_FOUND);
            }
            return resource;
        } catch (Exception exception) {
            throw new FileHandler(ErrorStatus.FILE_INCORRECT_URL);
        }
    }
}
