package com.nanshan.springbootnginxreverseproxy.controller;

import com.nanshan.springbootnginxreverseproxy.model.DeptVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

/**
 * @author RogerLo
 * @date 2023/7/4
 */
@RestController
@RequestMapping("/TestValidController")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Slf4j
public class TestValidController {

    @PostMapping("/getDeptData1")
    public ResponseEntity<DeptVO> getDeptData1(@Valid @RequestBody(required = false) DeptVO deptVO) {
        log.info("deptVO: {}", deptVO);
        return ResponseEntity.of(Optional.ofNullable(deptVO));
    }

    @PostMapping("/getDeptData2")
    public ResponseEntity<?> getDeptData2(
            @Valid @RequestBody(required = false) DeptVO deptVO, BindingResult bindingResult) {
        log.info("deptVO: {}", deptVO);
        if (bindingResult.hasErrors()) {
            // 處理驗證錯誤
            String errorMessage = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .reduce("", (s1, s2) -> s1 + "\n" + s2);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
        return ResponseEntity.of(Optional.ofNullable(deptVO));
    }
}
