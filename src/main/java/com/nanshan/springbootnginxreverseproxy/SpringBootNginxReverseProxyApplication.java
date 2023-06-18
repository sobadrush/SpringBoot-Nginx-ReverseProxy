package com.nanshan.springbootnginxreverseproxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;

import java.util.Arrays;

/**
 * CommandLineRunner: https://z.itpub.net/article/detail/9359DFC80B3615560719EA1529CD2520
 */
@SpringBootApplication
@EnableConfigurationProperties(value = ApplicationProps.class)
public class SpringBootNginxReverseProxyApplication implements CommandLineRunner {

    @Autowired
    private Environment springEnv;

    @Autowired
    private ApplicationProps applicationProps;

    @Value("${application.email}")
    private String email;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootNginxReverseProxyApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("... CommandLineRunner run() ...");
        Arrays.stream(springEnv.getActiveProfiles())
            .forEach(profile -> {
                System.err.println("【Active Profile】：" + profile);
            });
        System.err.println(" >>> email = " + email);
        System.err.println(" >>> applicationProps.getServers = " + applicationProps.getServers());
        System.err.println(applicationProps.getPurchase_info());
    }
}
