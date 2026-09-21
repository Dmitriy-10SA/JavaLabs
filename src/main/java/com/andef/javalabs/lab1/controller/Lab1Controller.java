package com.andef.javalabs.lab1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lab1")
public class Lab1Controller {

    @GetMapping("/hello")
    public String hello(@RequestParam(required = false) String name) {
        return name == null || name.isBlank() ? "Hello world!" : "Hello " + name;
    }

    @GetMapping("/about")
    public String about() {
        return "about us";
    }

    @GetMapping("/options")
    public String options(@RequestParam(required = false) String option) {
        return option == null ? "options" : "not an option";
    }
}
