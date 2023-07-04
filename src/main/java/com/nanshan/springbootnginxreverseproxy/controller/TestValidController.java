package com.nanshan.springbootnginxreverseproxy.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author RogerLo
 * @date 2023/7/4
 */
@RestController
@RequestMapping("/TestValidController")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Slf4j
public class TestValidController {

}
