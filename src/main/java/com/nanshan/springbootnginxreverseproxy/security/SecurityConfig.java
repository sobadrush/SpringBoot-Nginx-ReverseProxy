package com.nanshan.springbootnginxreverseproxy.security;

import com.nanshan.springbootnginxreverseproxy.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author RogerLo
 * @date 2023/6/30
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthService authService;

    @Autowired
    private JWTAuthenticationFilter jwtAuthenticationFilter;

    /**
     * 管理 Authentication 元件
     */
    @Bean("authenticationManagerBean")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // ref. 特定URL不使用 SpringSecurity: https://liba.ro/5zf7vqa32
        web.ignoring().antMatchers("/h2-console/**");
        web.ignoring().antMatchers("/my-actuator/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()// JWT，所以可以防範 csrf 攻擊，因此將其 disable
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, "/auth/**").permitAll() // 允許通過取得token方法
            // .antMatchers(HttpMethod.GET, "/HelloWorld/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // JWT 機制，所以不應該產生 Session，因此將 Session 建立取消
            .and()
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // 因為每個受保護的 API 請求都需要去檢查其 Header 上面的 token 是否合法，因此添加了一個自定義的 jwt filter 在 UsernamePasswordAuthenticationFilter 前面，這樣可以避免用原生 Spring Security 的方式幫我們做檢查，而是採用我們自定義的方式
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // auth.authenticationProvider(new DaoAuthenticationProvider()); // 向 AuthenticationManager 提供 AuthenticationProvider 來達成轉換 Authentication 的功能
        auth.userDetailsService(authService)
                .passwordEncoder(this.passwordEncoder());
    }

    /**
     * 密碼雜湊設定
     * 採用 BCrypt 演算法進行雜湊
     * BCrypt是一種密碼哈希函式和密碼加密演算法。它採用適用於 Blowfish 密碼演算法的改良版本，並使用適當的鹽值（Salt）和指定的計算迭代次數來增加安全性
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 若 DB 中的密碼不想加密，可使用 NoOpPasswordEncoder
     */
    // @Bean
    // public PasswordEncoder passwordEncoder() {
    //     return NoOpPasswordEncoder.getInstance();
    // }
}
