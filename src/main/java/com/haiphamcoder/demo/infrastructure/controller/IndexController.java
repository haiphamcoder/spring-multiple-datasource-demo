package com.haiphamcoder.demo.infrastructure.controller;

import org.springframework.web.bind.annotation.RestController;

import com.haiphamcoder.demo.shared.ApiResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class IndexController {

    @GetMapping("/")
    public ResponseEntity<ApiResponse<String>> index(@RequestParam(value = "name", defaultValue = "World") String name) {
        return ResponseEntity.ok().body(new ApiResponse<>(200, "Hello, " + name + "!", null, null, null));
    }
    
}
