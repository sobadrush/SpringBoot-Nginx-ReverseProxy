package com.nanshan.springbootnginxreverseproxy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author RogerLo
 * @date 2023/6/11
 */
@RestController
public class TestController {

    @GetMapping(value = "/HelloWorld", produces = { "text/plain" })
    public String helloWorld() {
        return "Hello World";
    }

}
