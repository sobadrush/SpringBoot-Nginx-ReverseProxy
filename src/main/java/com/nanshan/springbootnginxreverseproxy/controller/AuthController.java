package com.nanshan.springbootnginxreverseproxy.controller;

import com.nanshan.springbootnginxreverseproxy.model.AuthRequest;
import com.nanshan.springbootnginxreverseproxy.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author RogerLo
 * @date 2023/7/1
 */
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(value = "/getToken")
    public ResponseEntity<?> getToken(@RequestBody AuthRequest authRequest) {
        return authService.getToken(authRequest);
    }

}
