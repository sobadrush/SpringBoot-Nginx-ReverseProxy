package com.nanshan.springbootnginxreverseproxy.controller;

import com.nanshan.springbootnginxreverseproxy.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author RogerLo
 * @date 2023/7/4
 */
@RestController
@RequestMapping("/TestResponseStatusController")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Slf4j
public class TestResponseStatusController {

    @GetMapping(value = "/helloResponseStatus404")
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "自訂 404-NotFound")
    public void helloResponseStatus(@RequestParam("empName") String empName) {
        log.info("... enter helloResponseStatus404 ...");
        log.info("empName: {}", empName);
    }

    @PostMapping(value = "/helloResponseStatus200")
    @ResponseStatus(value = HttpStatus.OK, reason = "自訂 200-Success")
    public void helloResponseStatus1(@RequestParam(value = "empName") String empName) {
        log.info("... enter helloResponseStatus200 ...");
        log.info("empName: {}", empName);
    }

    @GetMapping(value = "/helloResponseStatus500")
    public void helloResponseStatus2(@RequestParam("empName") String empName) {
        log.info("... enter helloResponseStatus500 ...");
        log.info("empName: {}", empName);
        if (StringUtils.isBlank(empName)) {
            throw new BusinessException(); // 自訂 Exception
        }
    }

}
