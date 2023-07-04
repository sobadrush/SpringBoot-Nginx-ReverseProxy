package com.nanshan.springbootnginxreverseproxy.controller;

import com.nanshan.springbootnginxreverseproxy.exception.BusinessException;
import com.nanshan.springbootnginxreverseproxy.model.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;

/**
 * @author RogerLo
 * @date 2023/7/4
 */
@Slf4j
public class BaseController {

    @ExceptionHandler
    // 注意：若使用 reason 屬性，會強制回應為 HTML TYPE
    // @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "系統忙碌中，請稍後再試")
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<ApiResult> exceptionHandler(Exception ex, HttpServletResponse response) {
        if (ex instanceof BusinessException) {
            log.error("BusinessException: {}", ExceptionUtils.getRootCause(ex).toString());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResult<String>("失敗", ex));
        } else if (ex instanceof java.lang.ArithmeticException) {
            log.error("ArithmeticException: {}", ExceptionUtils.getRootCause(ex).toString());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResult<String>("失敗", ex));
        } else {
            log.error("Exceptions: {}", ex.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResult<>("系統發生內部錯誤！"));
        }
    }

}
