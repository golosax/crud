package com.ce.crud.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AppErrorController implements ErrorController {

    @RequestMapping("/error")
    @ResponseBody
    String errorMessage() {
        return "Page doesn't exist";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}