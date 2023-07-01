package com.nanshan.springbootnginxreverseproxy.service;

import com.nanshan.springbootnginxreverseproxy.model.AuthRequest;
import com.nanshan.springbootnginxreverseproxy.model.UserVO;
import com.nanshan.springbootnginxreverseproxy.repository.UserRepository;
import com.nanshan.springbootnginxreverseproxy.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author RogerLo
 * @date 2023/6/30
 */
@Service
@Slf4j
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Lazy
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserVO> instance = userRepository.findByEmail(email);
        if (instance.isPresent()) {
            return instance.get();
        }
        throw new UsernameNotFoundException("email not found");
    }

    /**
     * 取得 JWT Token
     * @param authRequest: 自訂的請求參數
     */
    public ResponseEntity<?> getToken(AuthRequest authRequest) {
        try {
            log.info("...取得 JWT Token...");
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
            UserVO user = userRepository.findByEmail(authRequest.getEmail()).get();
            String token = jwtUtil.Sign(user.getId().toString());
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);
            return ResponseEntity.ok().headers(headers).body(null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

}