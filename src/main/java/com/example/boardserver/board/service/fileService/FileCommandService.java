package com.example.boardserver.board.service.fileService;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileCommandService {

    /**
     * 1개 이상의 이미지를 업로드하는 Service
     * @param file MultipartFile
     * @return String
     */
    String upLoadFile(MultipartFile file);

    /**
     * file 이름 입력 시 해당 이미지 파일을 가져오는 Service
     * @param filename String
     * @return Resource
     */
    Resource getImage(String filename);
}
