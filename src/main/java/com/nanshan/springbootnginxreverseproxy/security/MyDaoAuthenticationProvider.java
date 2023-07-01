package com.nanshan.springbootnginxreverseproxy.security;

import com.nanshan.springbootnginxreverseproxy.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author RogerLo
 * @date 2023/7/1
 */
@Component
@Slf4j
public class MyDaoAuthenticationProvider extends DaoAuthenticationProvider {

    @Lazy
    @Autowired
    private AuthService authService;

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    private void init() {
        super.setUserDetailsService(authService);
        super.setPasswordEncoder(passwordEncoder);
    }

    /**
     * 可在此進行額外的身份認證檢查
     */
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        log.info(">>> enter into [MyDaoAuthenticationProvider].additionalAuthenticationChecks ...");
        super.additionalAuthenticationChecks(userDetails, authentication);
    }

}
