package com.nanshan.springbootnginxreverseproxy;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;

/**
 * @author RogerLo
 * @date 2023/6/16
 *
 * For SpringBoot 使用外部 Tomcat
 */
@Configuration
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringBootNginxReverseProxyApplication.class);
    }

}
