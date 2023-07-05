package com.nanshan.springbootnginxreverseproxy.controller;

import com.nanshan.springbootnginxreverseproxy.exception.MySystemException;
import com.nanshan.springbootnginxreverseproxy.model.DeptVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.SecureRandom;
import java.util.Optional;

/**
 * 測試觸發 GlobalExceptionHandler
 *
 * @author RogerLo
 * @date 2023/7/4
 */
@RestController
@RequestMapping("/TestGlobalExceptionHandlerController")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Slf4j
public class TestGlobalExceptionHandlerController {

    @GetMapping(value = "/triggerGlobalExceptionHandler")
    public void triggerGlobalExceptionHandler() {
        log.info("... enter triggerGlobalExceptionHandler ...");
        int rand = new SecureRandom().nextInt(3) + 1;
        switch (rand) {
            case 1:
                throw new MySystemException("權限不足", "503"); // 自訂 Exception
            case 2:
                throw new MySystemException("參數錯誤", "507"); // 自訂 Exception
            case 3:
                System.out.println(1/0);
                break;
            case 4:
                throw new RuntimeException("執行時期錯誤");
        }
    }

    @PostMapping("/getDeptData1")
    public ResponseEntity<DeptVO> getDeptData1(@Valid @RequestBody(required = false) DeptVO deptVO) {
        log.info("deptVO: {}", deptVO);
        return ResponseEntity.of(Optional.ofNullable(deptVO));
    }

}
