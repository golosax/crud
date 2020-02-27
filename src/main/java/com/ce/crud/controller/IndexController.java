package com.ce.crud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {// implements ErrorController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

//    @RequestMapping("/error")
//    @ResponseBody
//    String errorMessage() {
//        return "Ooops...";
//    }
//
//    @Override
//    public String getErrorPath() {
//        return "/error";
//    }
}