package com.example.boardserver.board.controller;

import com.example.boardserver.board.service.fileService.FileCommandService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
@Tag(name = "File", description = "Image File 관련 API")
public class FileController {

    private final FileCommandService fileCommandService;

    @PostMapping("/upload")
    public String upload(
            @RequestParam("file") MultipartFile file
    ) {
        return fileCommandService.upLoadFile(file);
    }

    @GetMapping(value = "{fileName}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public Resource getImage(
            @PathVariable("fileName") String fileName
    ) {
        return fileCommandService.getImage(fileName);
    }
}
