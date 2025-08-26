package com.example.springgsm.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String Hello() {
        return "나는 이상혁이야";
    }
}
