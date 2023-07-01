package com.nanshan.springbootnginxreverseproxy.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author RogerLo
 * @date 2023/6/30
 */
@Data
@AllArgsConstructor
public class AuthRequest {

    private String email;
    private String password;

}