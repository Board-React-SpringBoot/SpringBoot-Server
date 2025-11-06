package com.example.boardserver.common.controller;

import com.example.boardserver.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class TestController {

    @GetMapping()
    public ApiResponse<String> testMain() {
        return ApiResponse.onSuccess("Main Page");
    }
}
