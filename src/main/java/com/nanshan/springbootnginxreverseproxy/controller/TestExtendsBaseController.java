package com.nanshan.springbootnginxreverseproxy.controller;

import com.nanshan.springbootnginxreverseproxy.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.SecureRandom;

/**
 * @author RogerLo
 * @date 2023/7/4
 */
@RestController
@RequestMapping("/TestExtendsBaseController")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Slf4j
public class TestExtendsBaseController extends BaseController {

    @GetMapping(value = "/testTriggerExceptionHandler")
    public void testTriggerExceptionHandler() {
        log.info("... enter testTriggerExceptionHandler ...");
        int rand = new SecureRandom().nextInt(3) + 1;
        switch (rand) {
            case 1:
                throw new BusinessException(); // 自訂 Exception
            case 2:
                System.out.println(1/0);
                break;
            case 3:
                throw new RuntimeException("執行時期錯誤");
        }
    }

}
